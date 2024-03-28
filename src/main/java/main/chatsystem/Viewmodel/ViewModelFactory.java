package main.chatsystem.Viewmodel;
import main.chatsystem.Model.Model;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;

    public ViewModelFactory(Model model) {
        this.loginViewModel = new LoginViewModel(model);
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
}
