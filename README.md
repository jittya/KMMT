# KMMT : Kotlin Multiplatform Mobile Template
## _Koltin Multiplatform Mobile Develpoment Simplified_

[![Kotlin](https://img.shields.io/badge/kotlin-1.4.32-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

[![image](https://github.com/jittya/KMMT/blob/master/KMultiplatform.png)](https://kotlinlang.org/lp/mobile/)


KMMT is a KMM based project template designed to simplify the KMM development.
It uses a simplified MVVM approch that can be shared both in android and iOS easily.

_Primary objective of this project is to help KMM Developers & promote KMM technology_

## How to use
#### Shared Module :
##### _Step 1 : Define View_

- Create a View interface by extending from BaseView
- Define UI binding functions in View interface

```sh
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
-  Create a ViewModel class by extending from BaseViewModel with View as Type
-  Define your business logic in ViewModel class

```sh
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
        val username=getView()?.getEnteredUsername()
        val password=getView()?.getEnteredPassword()
        checkValidation(username,password)
    }

    fun checkValidation(username:String?,password:String?)
    {
        if (username.isNullOrBlank().not()&&password.isNullOrBlank().not())
        {
            getView()?.showPopUpMessage("Login Success","Username : $username\nPassword : $password")
        }
        else
        {
            getView()?.showPopUpMessage("Validation Failed","Username or Password is empty")
        }
    }
}
```
#### Android Module  :
##### _Step 3 : Define Android View_
- Create new activity by extending from KMMActivity with ViewModel as Type
- Implement created View interface in activity
- Implement all necessary methods from View & KMMActivity
##### Implement _LoginView_
```sh
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
        binding.loginBtn.setOnClickListener {
            onLoginClick.invoke()
        }
    }

    override fun setLoginButtonLabel(loginLabel: String) {
        binding.loginBtn.text=loginLabel
    }

    override fun showErrorMessageOnUsername(errorMsg: String) {
        binding.usernameET.error = errorMsg
    }

    //Generated Methods from KMMActivity based on LoginViewModel
    override fun initializeViewModel(): LoginViewModel {
        return LoginViewModel(this)
    }
}
```
#### iOS Module (Xcode) :
##### _Step 4 : Define iOS View_
- Create new viewcontroller by extending from KMMUIViewController
- Implement created View interface in viewcontroller
- Implement all necessary methods from View & KMMUIViewController
##### Implement _LoginView_
```sh
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
    
    func showErrorMessageOnUsername(errorMsg: String) {
        usernameTF.errorMessage=errorMsg
    }
    
    //Generated Methods from KMMUIViewController
    override func initializeViewModel() -> BaseViewModel<BaseView> {
        return LoginViewModel(view: self).getViewModel()
    }
}
```


## ✨Features ✨

#### Common Networking API builder ( [Ktor] )
Create API Services using BaseAPI class
```sh
class ProfileMicroServiceAPI : BaseAPI() {

    override val baseUrl: String
        get() = "https://mocki.io/"

    suspend fun getProfile(): ProfileModel {
        return HTTPHelper().doGet<ProfileModel> {
            apiPath("v1/e2c58213-cd6a-4e18-a170-83daf39b2f6c")
        }
    }
}
```

#### Async helper in ViewModel : Background Thread Helper ( [Kotlinx.Coroutines] )
Run code (Netwoking calls, Heavy calculations, Large dataSets from local DB) in Background Thead and get the result in UI Thread
```sh
class LoginViewModel(view: LoginView) : BaseViewModel<LoginView>(view) {

    fun getProfileData() {
        runOnBackground<ProfileModel>{
            ProfileMicroServiceAPI()::getProfile
        }.resultOnUI {
            getView()?.showPopUpMessage("Profile", "Username : ${it.name}\n Github : ${it.github}")
        }
    }

}
```

##### _Subscribe for upcoming details and features..._



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [Ktor]: <https://github.com/ktorio/ktor>
   [Kotlinx.Coroutines]: <https://github.com/Kotlin/kotlinx.coroutines>
