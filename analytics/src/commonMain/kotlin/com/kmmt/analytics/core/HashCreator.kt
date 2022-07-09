package com.kmmt.analytics.core

import kotlin.math.min

fun String.hash256(): String {
    var byteArray = this.encodeToByteArray()
    var sha = SHA256.digest(byteArray)
    return sha.base64
}


class SHA256 : SHA(chunkSize = 64, digestSize = 32) {
    companion object : HasherFactory({ SHA256() }) {
        private val H = intArrayOf(
            0x6a09e667, -0x4498517b, 0x3c6ef372, -0x5ab00ac6,
            0x510e527f, -0x64fa9774, 0x1f83d9ab, 0x5be0cd19
        )

        private val K = intArrayOf(
            0x428a2f98, 0x71374491, -0x4a3f0431, -0x164a245b,
            0x3956c25b, 0x59f111f1, -0x6dc07d5c, -0x54e3a12b,
            -0x27f85568, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, -0x7f214e02, -0x6423f959, -0x3e640e8c,
            -0x1b64963f, -0x1041b87a, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            -0x67c1aeae, -0x57ce3993, -0x4ffcd838, -0x40a68039,
            -0x391ff40d, -0x2a586eb9, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, -0x7e3d36d2, -0x6d8dd37b,
            -0x5d40175f, -0x57e599b5, -0x3db47490, -0x3893ae5d,
            -0x2e6d17e7, -0x2966f9dc, -0xbf1ca7b, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, -0x7b3787ec, -0x7338fdf8,
            -0x6f410006, -0x5baf9315, -0x41065c09, -0x398e870e
        )
    }


    private val h = IntArray(8)
    private val r = IntArray(8)
    private val w = IntArray(64)

    init {
        coreReset()
    }

    override fun coreReset(): Unit = run { arraycopy(H, 0, h, 0, 8) }

    override fun coreUpdate(chunk: ByteArray) {
        arraycopy(h, 0, r, 0, 8)

        for (j in 0 until 16) w[j] = chunk.readS32_be(j * 4)
        for (j in 16 until 64) {
            val s0 = w[j - 15].rotateRight(7) xor w[j - 15].rotateRight(18) xor w[j - 15].ushr(3)
            val s1 = w[j - 2].rotateRight(17) xor w[j - 2].rotateRight(19) xor w[j - 2].ushr(10)
            w[j] = w[j - 16] + s0 + w[j - 7] + s1
        }

        for (j in 0 until 64) {
            val s1 = r[4].rotateRight(6) xor r[4].rotateRight(11) xor r[4].rotateRight(25)
            val ch = r[4] and r[5] xor (r[4].inv() and r[6])
            val t1 = r[7] + s1 + ch + K[j] + w[j]
            val s0 = r[0].rotateRight(2) xor r[0].rotateRight(13) xor r[0].rotateRight(22)
            val maj = r[0] and r[1] xor (r[0] and r[2]) xor (r[1] and r[2])
            val t2 = s0 + maj
            r[7] = r[6]
            r[6] = r[5]
            r[5] = r[4]
            r[4] = r[3] + t1
            r[3] = r[2]
            r[2] = r[1]
            r[1] = r[0]
            r[0] = t1 + t2

        }
        for (j in 0 until 8) h[j] += r[j]
    }

    override fun coreDigest(out: ByteArray) {
        for (n in out.indices) out[n] = (h[n / 4] ushr (24 - 8 * (n % 4))).toByte()
    }
}

fun ByteArray.sha256() = hash(SHA256)


abstract class SHA(chunkSize: Int, digestSize: Int) : Hasher(chunkSize, digestSize) {
    override fun corePadding(totalWritten: Long): ByteArray {
        val tail = totalWritten % 64
        val padding = (if (64 - tail >= 9) 64 - tail else 128 - tail)
        val pad = ByteArray(padding.toInt()).apply { this[0] = 0x80.toByte() }
        val bits = (totalWritten * 8)
        for (i in 0 until 8) pad[pad.size - 1 - i] = ((bits ushr (8 * i)) and 0xFF).toByte()
        return pad
    }
}


open class HasherFactory(val create: () -> Hasher) {
    fun digest(data: ByteArray) = create().also { it.update(data, 0, data.size) }.digest()

    inline fun digest(temp: ByteArray = ByteArray(0x1000), readBytes: (data: ByteArray) -> Int): Hash =
        this.create().also {
            while (true) {
                val count = readBytes(temp)
                if (count <= 0) break
                it.update(temp, 0, count)
            }
        }.digest()
}

abstract class Hasher(val chunkSize: Int, val digestSize: Int) {
    private val chunk = ByteArray(chunkSize)
    private var writtenInChunk = 0
    private var totalWritten = 0L

    fun reset(): Hasher {
        coreReset()
        writtenInChunk = 0
        totalWritten = 0L
        return this
    }

    fun update(data: ByteArray, offset: Int, count: Int): Hasher {
        var curr = offset
        var left = count
        while (left > 0) {
            val remainingInChunk = chunkSize - writtenInChunk
            val toRead = min(remainingInChunk, left)
            arraycopy(data, curr, chunk, writtenInChunk, toRead)
            left -= toRead
            curr += toRead
            writtenInChunk += toRead
            if (writtenInChunk >= chunkSize) {
                writtenInChunk -= chunkSize
                coreUpdate(chunk)
            }
        }
        totalWritten += count
        return this
    }

    fun digestOut(out: ByteArray) {
        val pad = corePadding(totalWritten)
        var padPos = 0
        while (padPos < pad.size) {
            val padSize = chunkSize - writtenInChunk
            arraycopy(pad, padPos, chunk, writtenInChunk, padSize)
            coreUpdate(chunk)
            writtenInChunk = 0
            padPos += padSize
        }

        coreDigest(out)
        coreReset()
    }

    protected abstract fun coreReset()
    protected abstract fun corePadding(totalWritten: Long): ByteArray
    protected abstract fun coreUpdate(chunk: ByteArray)
    protected abstract fun coreDigest(out: ByteArray)

    fun update(data: ByteArray) = update(data, 0, data.size)
    fun digest(): Hash = Hash(ByteArray(digestSize).also { digestOut(it) })
}

inline class Hash(val bytes: ByteArray) {
    companion object {
        fun fromHex(hex: String): Hash = Hash(Hex.decode(hex))
        fun fromBase64(base64: String): Hash = Hash(Base64.decodeIgnoringSpaces(base64))
    }
    val base64 get() = Base64.encode(bytes)
    val hex get() = Hex.encode(bytes)
    val hexLower get() = Hex.encodeLower(bytes)
    val hexUpper get() = Hex.encodeUpper(bytes)
}

fun ByteArray.hash(algo: HasherFactory): Hash = algo.digest(this)

internal inline fun Int.ext8(offset: Int) = (this ushr offset) and 0xFF

internal fun Int.rotateRight(amount: Int): Int = (this ushr amount) or (this shl (32 - amount))
internal fun Int.rotateLeft(bits: Int): Int = ((this shl bits) or (this ushr (32 - bits)))

internal fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, count: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + count)
internal fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, count: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + count)

internal fun ByteArray.readU8(o: Int): Int = this[o].toInt() and 0xFF
internal fun ByteArray.readS32_be(o: Int): Int =
    (readU8(o + 3) shl 0) or (readU8(o + 2) shl 8) or (readU8(o + 1) shl 16) or (readU8(o + 0) shl 24)



object Hex {
    private const val DIGITS = "0123456789ABCDEF"
    val DIGITS_UPPER = DIGITS.toUpperCase()
    val DIGITS_LOWER = DIGITS.toLowerCase()

    fun isHexDigit(c: Char): Boolean = decodeChar(c) >= 0
    fun decodeHexDigit(c: Char): Int {
        val result = decodeChar(c)
        if (result < 0) error("Invalid hex digit '$c'")
        return result
    }
    fun decodeChar(c: Char): Int = when (c) {
        in '0'..'9' -> c - '0'
        in 'a'..'f' -> c - 'a' + 10
        in 'A'..'F' -> c - 'A' + 10
        else -> -1
    }

    fun encodeCharLower(v: Int): Char = DIGITS_LOWER[v]
    fun encodeCharUpper(v: Int): Char = DIGITS_UPPER[v]

    operator fun invoke(v: String) = decode(v)
    operator fun invoke(v: ByteArray) = encode(v)

    fun appendHexByte(appender: Appendable, value: Int) {
        appender.append(encodeCharLower((value ushr 4) and 0xF))
        appender.append(encodeCharLower((value ushr 0) and 0xF))
    }

    fun decode(str: String, out: ByteArray = ByteArray(str.length / 2)): ByteArray =
        out.also { decode(str) { n, byte -> out[n] = byte } }

    inline fun decode(str: String, out: (Int, Byte) -> Unit) {
        for (n in 0 until str.length / 2) {
            val c0 = decodeHexDigit(str[n * 2 + 0])
            val c1 = decodeHexDigit(str[n * 2 + 1])
            out(n, ((c0 shl 4) or c1).toByte())
        }
    }

    fun encode(src: ByteArray): String = encodeBase(src, DIGITS_LOWER)
    fun encodeLower(src: ByteArray): String = encodeBase(src, DIGITS_LOWER)
    fun encodeUpper(src: ByteArray): String = encodeBase(src, DIGITS_UPPER)

    fun encode(src: ByteArray, dst: Appendable) = encode(src, dst, DIGITS_LOWER)
    fun encodeLower(src: ByteArray, dst: Appendable) = encode(src, dst, DIGITS_LOWER)
    fun encodeUpper(src: ByteArray, dst: Appendable) = encode(src, dst, DIGITS_UPPER)

    private fun encode(src: ByteArray, dst: Appendable, digits: String) {
        for (n in src.indices) {
            val v = src[n].toInt() and 0xFF
            dst.append(digits[(v ushr 4) and 0xF])
            dst.append(digits[(v ushr 0) and 0xF])
        }
    }

    private fun encodeBase(data: ByteArray, digits: String = DIGITS): String = buildString(data.size * 2) {
        encode(data, this, digits)
    }
}

fun Appendable.appendHexByte(value: Int) = Hex.appendHexByte(this, value)

fun String.fromHex(): ByteArray = Hex.decode(this)
val ByteArray.hexLower: String get() = Hex.encodeLower(this)
val ByteArray.hexUpper: String get() = Hex.encodeUpper(this)

fun Char.isHexDigit() = Hex.isHexDigit(this)

val List<String>.unhexIgnoreSpaces get() = joinToString("").unhexIgnoreSpaces
val String.unhexIgnoreSpaces: ByteArray get() = buildString {
    val str = this@unhexIgnoreSpaces
    for (n in 0 until str.length) {
        val c = str[n]
        if (c != ' ' && c != '\t' && c != '\n' && c != '\r') append(c)
    }
}.unhex
val String.unhex get() = Hex.decode(this)
val ByteArray.hex get() = Hex.encodeLower(this)

val Int.hex: String get() = "0x$shex"
val Int.shex: String
    get() {
        var out = ""
        for (n in 0 until 8) {
            val v = (this ushr ((7 - n) * 4)) and 0xF
            out += Hex.encodeCharUpper(v)
        }
        return out
    }


object Base64 {
    private val TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
    private val DECODE = IntArray(0x100).apply {
        for (n in 0..255) this[n] = -1
        for (n in 0 until TABLE.length) {
            this[TABLE[n].toInt()] = n
        }
    }

    operator fun invoke(v: String) = decodeIgnoringSpaces(v)
    operator fun invoke(v: ByteArray) = encode(v)

    fun decode(str: String): ByteArray {
        val src = ByteArray(str.length) { str[it].toByte() }
        val dst = ByteArray(src.size)
        return dst.copyOf(decode(src, dst))
    }

    fun decodeIgnoringSpaces(str: String): ByteArray {
        return decode(str.replace(" ", "").replace("\n", "").replace("\r", ""))
    }

    fun decode(src: ByteArray, dst: ByteArray): Int {
        var m = 0

        var n = 0
        while (n < src.size) {
            val d = DECODE[src.readU8(n)]
            if (d < 0) {
                n++
                continue // skip character
            }

            val b0 = if (n < src.size) DECODE[src.readU8(n++)] else 64
            val b1 = if (n < src.size) DECODE[src.readU8(n++)] else 64
            val b2 = if (n < src.size) DECODE[src.readU8(n++)] else 64
            val b3 = if (n < src.size) DECODE[src.readU8(n++)] else 64
            dst[m++] = (b0 shl 2 or (b1 shr 4)).toByte()
            if (b2 < 64) {
                dst[m++] = (b1 shl 4 or (b2 shr 2)).toByte()
                if (b3 < 64) {
                    dst[m++] = (b2 shl 6 or b3).toByte()
                }
            }
        }
        return m
    }

    @Suppress("UNUSED_CHANGED_VALUE")
    fun encode(src: ByteArray): String {
        val out = StringBuilder((src.size * 4) / 3 + 4)
        var ipos = 0
        val extraBytes = src.size % 3
        while (ipos < src.size - 2) {
            val num = src.readU24BE(ipos)
            ipos += 3

            out.append(TABLE[(num ushr 18) and 0x3F])
            out.append(TABLE[(num ushr 12) and 0x3F])
            out.append(TABLE[(num ushr 6) and 0x3F])
            out.append(TABLE[(num ushr 0) and 0x3F])
        }

        if (extraBytes == 1) {
            val num = src.readU8(ipos++)
            out.append(TABLE[num ushr 2])
            out.append(TABLE[(num shl 4) and 0x3F])
            out.append('=')
            out.append('=')
        } else if (extraBytes == 2) {
            val tmp = (src.readU8(ipos++) shl 8) or src.readU8(ipos++)
            out.append(TABLE[tmp ushr 10])
            out.append(TABLE[(tmp ushr 4) and 0x3F])
            out.append(TABLE[(tmp shl 2) and 0x3F])
            out.append('=')
        }

        return out.toString()
    }

    private fun ByteArray.readU8(index: Int): Int = this[index].toInt() and 0xFF
    private fun ByteArray.readU24BE(index: Int): Int =
        (readU8(index + 0) shl 16) or (readU8(index + 1) shl 8) or (readU8(index + 2) shl 0)
}

fun String.fromBase64IgnoreSpaces(): ByteArray =
    Base64.decode(this.replace(" ", "").replace("\n", "").replace("\r", ""))
fun String.fromBase64(ignoreSpaces: Boolean = false): ByteArray = if (ignoreSpaces) Base64.decodeIgnoringSpaces(
    this
) else Base64.decode(this)
fun ByteArray.toBase64(): String = Base64.encode(this)
val ByteArray.base64: String get() = Base64.encode(this)