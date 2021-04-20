package kg.izapp.testapp.ui.login

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kg.izapp.testapp.R
import kg.izapp.testapp.data.Event
import kg.izapp.testapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        subscribeToLiveData()
        setupViews()
    }

    @SuppressLint("RestrictedApi")
    private fun subscribeToLiveData() {
        loginViewModel.event.observe(this, Observer {
            when (it) {
                is Event.InCorrectData -> {
                    username.isErrorEnabled = true
                    inp_username.supportBackgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    password.isErrorEnabled = true
                    inp_password.supportBackgroundTintList =
                        ColorStateList.valueOf(resources.getColor(R.color.red))
                    password.error = getString(R.string.label_incorrect_data)
                }
                is Event.SuccessLogin -> {
                    MainActivity.start(this)
                }
            }
        })
    }

    private fun setupViews() {
        inp_username.addTextChangedListener(getTextWatcher())
        inp_password.addTextChangedListener(getTextWatcher())
        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            login.postDelayed({
                loading.visibility = View.GONE
                loginViewModel.checkUserData(
                    username.editText?.text.toString().trim(),
                    password.editText?.text.toString().trim()
                )
            }, 2000)
        }
    }

    private fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                toggleButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun toggleButtonState() {
        login.isEnabled = username.editText?.text.toString().trim().isNotEmpty()
                && password.editText?.text.toString().trim().isNotEmpty()
        password.error = ""
    }
}