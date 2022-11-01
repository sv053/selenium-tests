import '../SignUp.css'

const SignUpSection = () => {
    return (
        <div className="sign-up-form">
            <div className="sign-up-form-content">
                <h3 className="card-form-title">Sign up</h3>
                <div className="form-group mt-3">
                    <label>Email</label>
                    <input type="email"
                           className="form-control mt-1"
                           id="email"
                           placeholder="Enter email"/>
                </div>
                <div className="form-group mt-3">
                    <label>Password</label>
                    <input type="password"
                           className="form-control mt-1"
                           id="password"
                           placeholder="Enter password"/>
                </div>
                <div className="form-group mt-3">
                    <label>Repeat password</label>
                    <input type="password"
                           className="form-control mt-1"
                           id="repeatPassword"
                           placeholder="Repeat password"/>
                </div>
            </div>
        </div>
    )
}

export default SignUpSection
