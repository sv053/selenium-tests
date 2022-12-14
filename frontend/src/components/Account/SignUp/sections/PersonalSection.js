import '../SignUp.css'

const PersonalSection = () => {
    return (
        <div className="sign-up-form">
            <div className="sign-up-form-content">
                <h3 className="card-form-title">Personal data</h3>
                <div className="form-group mt-3">
                    <label>Name</label>
                    <input type="text"
                           className="form-control mt-1"
                           id="name"
                           required="true"
                           placeholder="Enter name"/>
                </div>
                <div className="form-group mt-3">
                    <label>Nationality</label>
                    <input type="text"
                           className="form-control mt-1"
                           id="nationality"
                           required="true"
                           placeholder="Enter nationality"/>
                </div>
                <div className="form-group mt-3">
                    <label>Passport number</label>
                    <input type="text"
                           className="form-control mt-1"
                           id="passportNumber"
                           required="true"
                           placeholder="Enter passport number"/>
                </div>
                <div className="form-group mt-3">
                    <label>Age</label>
                    <input type="number"
                           className="form-control mt-1"
                           id="age"
                           required="true"
                           placeholder="Enter age"/>
                </div>
            </div>
        </div>
    )
}

export default PersonalSection
