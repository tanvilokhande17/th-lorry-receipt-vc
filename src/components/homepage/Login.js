import React, {useState} from "react";
import {FormGroup, Dropdown, DropdownButton} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {TextInput} from "../common/TextInput";
import {useHistory} from "react-router-dom";
const Login = ({setPage}) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const history = useHistory();

  function validateForm() {
    return username.length > 0 && password.length > 0;
  }

  return (
    <>
      <div className="login-header">
        <HeaderText value="Login" />
      </div>
      <FormGroup controlId="username">
        <Label value="Username" />
        <TextInput value={username} setValue={setUsername} />
      </FormGroup>
      <FormGroup controlId="password">
        <Label value="Password" />
        <PasswordInput value={password} setValue={setPassword} />
      </FormGroup>
      <SubmitButton
        label="Login"
        submitHandler={() => history.push("/dashboard")}
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
