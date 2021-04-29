import React, {useEffect, useState} from "react";
import {Form} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {NumberInput} from "../common/NumberInput";
import axios from "axios";
import {LOGIN_URL} from "../util/Constant";
import RolesDropdown from "../common/RolesDropdown";
import {saveUser, getUser} from "../util/User";

const Login = ({setPage}) => {
  const [mobileNumber, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Transporter");
  const [submitDisabled, setSubmitDisabled] = useState(true);
  const user = getUser();

  useEffect(() => {
    if (user && user.isLogin === "true")
      window.location.pathname = "/dashboard";
  }, []);

  useEffect(() => {
    if (mobileNumber !== "" && password !== "" && role !== "")
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

              saveUser(
                res.user.fullName,
                mobileNumber,
                res.role,
                res.accessToken,
                true
              );
              window.location.pathname = "/dashboard";
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
    <Form>
      <div className="login-header">
        <HeaderText value="Login" />
      </div>
      <Form.Group controlId="mobileNumber">
        <Label value="Mobile Number" />
        <NumberInput value={mobileNumber} setValue={setMobileNumber} />
      </Form.Group>

      <Form.Group controlId="password">
        <Label value="Password" />
        <PasswordInput value={password} setValue={setPassword} />
      </Form.Group>
      <RolesDropdown setRole={setRole} />
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
    </Form>
  );
};
export default Login;
