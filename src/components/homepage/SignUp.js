import React, {useEffect, useState} from "react";
import {FormGroup, Form} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {TextInput} from "../common/TextInput";
import axios from "axios";
import {LOGIN_URL, SIGNUP_URL} from "../util/Constant";
import RolesDropdown from "../common/RolesDropdown";
import {useHistory} from "react-router";
import {NumberInput} from "../common/NumberInput";
import {saveUser} from "../util/User";

const SignUp = ({setPage}) => {
  const [name, setName] = useState("");
  const [mobileNo, setMobileNo] = useState("");
  const [otp, setOtp] = useState("");
  const [showVerifyOtp, setShowVerifyOtp] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Select Role");
  const [address, setAddress] = useState("");
  const [submitDisabled, setSubmitDisabled] = useState(true);
  const history = useHistory();

  const signupAccount = e => {
    e.preventDefault();
    setSubmitDisabled(true);
    axios
      .post(SIGNUP_URL, {
        fullName: name,
        mobileNumber: mobileNo,
        address: address,
        password: password,
        role: role,
      })
      .then(
        response => {
          console.log(response);
          if (response.status === 200) {
            const res = response.data;

            axios
              .post(LOGIN_URL, {
                mobileNumber: mobileNo,
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
                      res.role,
                      res.accessToken,
                      true
                    );
                    history.push("/dashboard");
                  } else {
                    alert(response.data.message);
                  }
                },
                error => {
                  console.log(error);
                }
              );
          }
          setSubmitDisabled(false);
        },
        error => {
          console.log(error);
          setSubmitDisabled(false);
        }
      );
  };

  useEffect(() => {
    if (
      name === "" ||
      mobileNo === "" ||
      password === "" ||
      address === "" ||
      role === "Select Role"
    )
      setSubmitDisabled(true);
    else setSubmitDisabled(false);
  });

  const verifyOTP = () => {
    if (otp === "1234") setOtpVerified(true);
    else alert("Invalid OTP");
  };
  const sendOTP = () => {
    if (mobileNo !== "") setShowVerifyOtp(true);
    else alert("Please enter valid mobile number.");
  };
  return (
    <>
      <HeaderText value="SignUp" />
      <FormGroup controlId="name">
        <Label value="Name" />
        <TextInput value={name} setValue={setName} />
      </FormGroup>
      <FormGroup controlId="mobileNo">
        <Label value="Mobile No" />
        <NumberInput maxlength="10" value={mobileNo} setValue={setMobileNo} />
      </FormGroup>

      {otpVerified ? (
        <>
          <FormGroup controlId="password">
            <Label value="Password" />
            <PasswordInput value={password} setValue={setPassword} />
          </FormGroup>
          <FormGroup controlId="address">
            <Label value="Address" />
            <Form.Control
              as="textarea"
              rows={3}
              value={address}
              onChange={e => setAddress(e.target.value)}
            />
          </FormGroup>
          <RolesDropdown role={role} setRole={setRole} />

          <SubmitButton
            disabled={submitDisabled}
            label="SignUp"
            submitHandler={signupAccount}
          ></SubmitButton>
        </>
      ) : (
        <>
          {showVerifyOtp ? (
            <>
              <FormGroup controlId="otp">
                <NumberInput value={otp} setValue={setOtp} />
              </FormGroup>
              <SubmitButton
                label="Verify OTP"
                submitHandler={verifyOTP}
              ></SubmitButton>
            </>
          ) : (
            <SubmitButton
              label="Send OTP"
              submitHandler={sendOTP}
            ></SubmitButton>
          )}
        </>
      )}

      <div className="signup-text">
        Already have account? Click here to{" "}
        <span onClick={() => setPage("login")} className="signup-link">
          Login
        </span>
        .
      </div>
    </>
  );
};
export default SignUp;
