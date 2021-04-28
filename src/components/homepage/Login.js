import React, {useContext, useEffect, useState} from "react";
import {FormGroup} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {NumberInput} from "../common/NumberInput";
import {useHistory} from "react-router-dom";
import axios from "axios";
import {LOGIN_URL} from "../util/Constant";
import RolesDropdown from "../common/RolesDropdown";
import {saveUser} from "../util/User";

const Login = ({setPage}) => {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Select Role");
  const [submitDisabled, setSubmitDisabled] = useState(true);
  const history = useHistory();

  useEffect(() => {
    if (mobileNumber !== "" && password !== "" && role !== "Select Role")
      setSubmitDisabled(false);
    else setSubmitDisabled(true);
  }, [mobileNumber, password, role]);

  const handleClick = e => {
    e.preventDefault();
    setSubmitDisabled(true);
    if (mobileNumber) {
      axios
        .post(LOGIN_URL, {
          mobileNumber: mobileNumber,
          password: password,
          role: role,
        })
        .then(
          response => {
            if (
              response.status === 200 &&
              response.data.message === "Sign In successful"
            ) {
              const res = response.data;

              saveUser(res.user.fullName, res.role, res.accessToken, true);
              history.push("/dashboard");
            } else {
              alert(response.data.message);
            }
            setSubmitDisabled(false);
          },
          error => {
            console.log(error);
            setSubmitDisabled(false);
          }
        );
    }
  };

  return (
    <>
      <div className="login-header">
        <HeaderText value="Login" />
      </div>
      <FormGroup controlId="mobileNumber">
        <Label value="Mobile Number" />
        <NumberInput value={mobileNumber} setValue={setMobileNumber} />
      </FormGroup>

      <FormGroup controlId="password">
        <Label value="Password" />
        <PasswordInput value={password} setValue={setPassword} />
      </FormGroup>
      <RolesDropdown role={role} setRole={setRole} />
      <SubmitButton
        label="Login"
        disabled={submitDisabled}
        submitHandler={handleClick}
      ></SubmitButton>
      <div className="signup-text">
        Don't have account? Click here to{" "}
        <span onClick={() => setPage("signup")} className="signup-link">
          SignUp
        </span>
        .
      </div>
    </>
  );
};
export default Login;
