# KMMT : Kotlin Multiplatform Mobile Template
## _Kotlin Multiplatform Mobile Development Simplified_

[![Kotlin](https://img.shields.io/badge/kotlin-1.4.32-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

[![image](https://github.com/jittya/KMMT/blob/master/KMultiplatform.png)](https://kotlinlang.org/lp/mobile/)


KMMT is a KMM based project template designed to simplify the KMM development.
It uses a simplified MVVM approach that can be shared both in android and iOS easily.

_Primary objective of this project is to help KMM Developers & promote KMM technology_

[![image](https://kotlinlang.org/lp/mobile/static/sdk-811ad35a3742e58b40278b7a984fc289.svg)](https://kotlinlang.org/lp/mobile/)

##### IDE Requirements
IntelliJ/Android Studio - Android & Shared Module

Xcode - iOS Project


## ✨Features ✨

#### Common Networking API builder ( [Ktor] )
Create API Services using BaseAPI class.
```kotlin
class JsonPlaceHolderServiceAPI : BaseAPI() {

    override val baseUrl: String
        get() = "https://jsonplaceholder.typicode.com/"

    suspend fun getPosts(postId: Int): List<PostModel> {
        return doGet {
            apiPath("comments?postId=$postId")
        }
    }

    suspend fun setPost(post: PostModel): PostModel {
        return doPost(post) {
            apiPath("comments")
        }
    }
}
```

#### Async Task Helper ( [Kotlinx.Coroutines] )
Run code (Networking calls, Heavy calculations, Large dataSets from local DB, etc..) in Background thread and get the result in UI thread.
```kotlin
class PostViewModel(view: LoginView) : BaseViewModel<LoginView>(view) {

    fun getPostsFromAPI() {
    
        runOnBackground(1){
            JsonPlaceHolderServiceAPI()::getPosts
        }.resultOnUI {
            getView()?.showPopUpMessage("First Post Details", "Username : ${it.first().name}\n email : ${it.first().email}")
        }
    }
    
    fun savePost() {
        
        val post = PostModel("Post Body", "jit@ccc.com", 100, "Jitty", 6)
        
        runOnBackground(post) {
            JsonPlaceHolderServiceAPI()::setPost
        }.resultOnUI {
            getView()?.showPopUpMessage("Saved Post Details", "Name : ${it.name}\n email : ${it.email}")
        }
    }
}
```

#### Multiplatform Bundle : Object Passing B/W Activities or ViewControllers
View Model can pass objects & values from Activity to Activity (Android) or ViewController to ViewController (iOS)

###### Send Values From 1st View Model
```kotlin
   // 1st View Model 
   
     var userModel = UserModel("jittya@gmail.com", "Jitty", "Andiyan")

     var bundle = Bundle {
         putStringExtra(HomeViewModel.USER_NAME, username.toString())
         putSerializableExtra(HomeViewModel.USER_OBJECT, userModel, UserModel.serializer())
     }
                    
     getView()?.navigateToHomePage(bundle)

     
   // 1st View 
   
     fun navigateToHomePage(bundle: BundleX)
     
     
   // 1st Activity : Android
   
       override fun navigateToHomePage(bundle: BundleX) {
           openActivity(HomeActivity::class.java,bundle)
           finish()
       }
    
   // 1st ViewContoller : iOS
       
       func navigateToHomePage(bundle: BundleX) {
           openViewController(newViewControllerName: "HomeViewController",bundle: bundle)
       }
    
```
###### Retrieve Values From 2nd View Model
```kotlin
   // 2nd View Model 
   
   class HomeViewModel(view: HomeView) : BaseViewModel<HomeView>(view) {

       companion object BundleKeys {
           const val USER_NAME = "USERNAME"
           const val USER_OBJECT = "USEROBJ"
       }

       override fun onStartViewModel() {
       
           getBundleValue<String>(USER_NAME)?.let { username ->
            
           }
           getBundleValue<UserModel>(USER_OBJECT)?.let { userModel ->
            
           }
       }
   }
```

#### Platform Specific Blocks in ViewModel
Execute anything specific to a particular platform using Platform Blocks
```kotlin

runOnAndroid {
            
}

runOniOS { 
            
}

```

#### Object Serialization Helper ( [Kotlinx.Serialization] )
Use **_toJsonString_** and **_toObject_** functions for instant serialization.

_Objects to String Serialization_
```koltin
        var userModel = UserModel("jittya@gmail.com", "Jitty", "Andiyan")
        
        var jsonString = userModel.toJsonString(UserModel.serializer())
```

_String to Object Serialization_
```koltin
        var userModel =j sonString.toObject<UserModel>()
        
        or
        
        var userModel:UserModel = jsonString.toObject()
        
        or
        
        var userModel = jsonString.toObject(UserModel.serializer())
```

#### Key Value Store ( [Multiplatform Settings] )
Use **_storeValue_** and **_getStoreValue_** functions in **ViewModel** for storing and retrieving Key-Value respectively

_Storing **Key-Value** pair_
```koltin
        var userModel = UserModel("jittya@gmail.com", "Jitty", "Andiyan")
        
        storeValue { 
            putString("Key1","Value")
            putBoolean("Key2",false)
            putSerializable("Key3",userModel,UserModel.serializer())
        }
```

_Retrieve **Value** using **Key**_
```koltin
        var stringValue = getStoreValue<String>("Key1")
        
        or
        
        var stringValue:String? = getStoreValue("Key1")
        
        var boolValue = getStoreValue<Boolean>("Key2")
        
        var userModel = getStoreValue<UserModel>("Key3",UserModel.serializer())
```

#### Local Database SQLite ( [SQLDelight] )
Please refer [SQLDelight]


## How to use
#### Shared Module (Business Logics & UI Binding Methods) :
##### _Step 1 : Define View_

- Create a View interface by extending from BaseView.
- Define UI binding functions in View interface.

```kotlin
interface LoginView : BaseView {

    fun setLoginPageLabel(msg:String)
    fun setUsernameLabel(usernameLabel:String)
    fun setPasswordLabel(passwordLabel:String)
    fun setLoginButtonLabel(loginLabel: String)
    
    fun getEnteredUsername():String
    fun getEnteredPassword():String

    fun setLoginButtonClickAction(onLoginClick: KFunction0<Unit>)
}
```

##### _Step 2 : Define ViewModel_
-  Create a ViewModel class by extending from BaseViewModel with View as Type.
-  Define your business logic in ViewModel class.

```kotlin
class LoginViewModel(view: LoginView) :BaseViewModel<LoginView>(view) {
    override fun onStartViewModel() {
        getView()?.setLoginPageLabel("Login : ${Platform().platform}")
        getView()?.setUsernameLabel("Enter Username")
        getView()?.setPasswordLabel("Enter Password")
        getView()?.setLoginButtonLabel("Login")
        getView()?.setLoginButtonClickAction(this::onLoginButtonClick)
    }

    fun onLoginButtonClick()
    {
        getView()?.showLoading("authenticating...")
        val username=getView()?.getEnteredUsername()
        val password=getView()?.getEnteredPassword()
        checkValidation(username,password)
    }

    fun checkValidation(username:String?,password:String?)
    {
        if (username.isNullOrBlank().not()&&password.isNullOrBlank().not())
        {
          val credentials = CredentialsModel(username.toString(), password.toString())
         
          runOnBackground(credentials) {
                JsonPlaceHolderServiceAPI()::authenticate
            }.resultOnUI {
                getView()?.dismissLoading()
                if (it) {
                    getView()?.navigateToHomePage(username.toString())
                } else {
                    getView()?.showPopUpMessage(
                        "Login Failed"
                    )
                }
            }
        }
        else
        {
            getView()?.showPopUpMessage("Validation Failed","Username or Password is empty")
        }
    }
}
```
#### Android Module UI Binding :
##### _Step 3 : Define Android View_
- Create new activity by extending from KMMActivity with ViewModel as Type.
- Implement created View interface in activity.
- Implement all necessary methods from View & KMMActivity.

Implement **_LoginView & Bind UI Controls_**
```kotlin
class LoginActivity : KMMActivity<LoginViewModel>(), LoginView {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    //Generated Methods from LoginView
    override fun setLoginPageLabel(msg: String) {
        binding.textView.text = msg
    }

    override fun setUsernameLabel(usernameLabel: String) {
        binding.usernameET.hint=usernameLabel
    }

    override fun setPasswordLabel(passwordLabel: String) {
        binding.passwordET.hint=passwordLabel
    }

    override fun getEnteredUsername(): String {
        return binding.usernameET.text.toString()
    }

    override fun getEnteredPassword(): String {
        return binding.passwordET.text.toString()
    }

    override fun setLoginButtonClickAction(onLoginClick: KFunction0<Unit>) {
        binding.loginBtn.setClickAction(onLoginClick)
    }

    override fun setLoginButtonLabel(loginLabel: String) {
        binding.loginBtn.text=loginLabel
    }

    //Generated Methods from KMMActivity based on LoginViewModel
    override fun initializeViewModel(): LoginViewModel {
        return LoginViewModel(this)
    }
}
```
#### iOS Module UI Binding (Xcode) :
##### _Step 4 : Define iOS View_
- Create new viewcontroller by extending from KMMUIViewController.
- Implement created View interface in viewcontroller.
- Implement all necessary methods from View & KMMUIViewController.

Implement **_LoginView  & Bind UI Controls_**
```kotlin
class LoginViewController: KMMUIViewController ,LoginView {
    
    @IBOutlet weak var usernameTF: UITextFieldX!
    @IBOutlet weak var passwordTF: UITextFieldX!
    @IBOutlet weak var textlabel: UILabel!
    @IBOutlet weak var loginBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    //Generated Methods from LoginView
    func setLoginPageLabel(msg: String) {
        textlabel.text=msg
    }
    
    func setUsernameLabel(usernameLabel: String) {
        usernameTF.placeholder=usernameLabel
    }
    
    func setPasswordLabel(passwordLabel: String) {
        passwordTF.placeholder=passwordLabel
    }
    
    func getEnteredUsername() -> String {
        usernameTF.errorMessage=""
        return usernameTF.text ?? ""
    }
    
    func getEnteredPassword() -> String {
        return passwordTF.text ?? ""
    }
    
    func setLoginButtonClickAction(onLoginClick: @escaping () -> KotlinUnit) {
        loginBtn.setClickAction(action: onLoginClick)
    }
    
    func setLoginButtonLabel(loginLabel: String) {
        loginBtn.setTitle(loginLabel, for: UIControl.State.normal)
    }
    
    //Generated Methods from KMMUIViewController
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return LoginViewModel(view: self).getViewModel()
    }
}
```


##### _Subscribe for upcoming details and features..._



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [Ktor]: <https://github.com/ktorio/ktor>
   [Kotlinx.Coroutines]: <https://github.com/Kotlin/kotlinx.coroutines>
   [SQLDelight]:<https://github.com/cashapp/sqldelight>
   [kotlinx.serialization]:<https://github.com/Kotlin/kotlinx.serialization>
   [Multiplatform Settings]:<https://github.com/russhwolf/multiplatform-settings>
