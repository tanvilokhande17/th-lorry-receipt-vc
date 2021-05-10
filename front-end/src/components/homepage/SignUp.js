import React, {useEffect, useState} from "react";
import {Form} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {TextInput} from "../common/TextInput";
import axios from "axios";
import {LOGIN_URL, SIGNUP_URL} from "../util/Constant";
import RolesDropdown from "../common/RolesDropdown";
import {NumberInput} from "../common/NumberInput";
import {saveUser} from "../util/User";

const SignUp = ({setPage}) => {
  const [name, setName] = useState("");
  const [mobileNo, setMobileNo] = useState("");
  const [otp, setOtp] = useState("");
  const [showVerifyOtp, setShowVerifyOtp] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Transporter");
  const [address, setAddress] = useState("");
  const [submitDisabled, setSubmitDisabled] = useState(true);

  const passwordReg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/gm;
  const signupAccount = e => {
    if (!passwordReg.test(password)) {
      alert("Password must contain at least one number, one uppercase, lowercase letter, and one special characters");
    } else {
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
                      mobileNo,
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
        },
        error => {
          console.log(error);
          setSubmitDisabled(false);
        }
      );
    }
  };

  useEffect(() => {
    if (
      name === "" ||
      mobileNo === "" ||
      password === "" ||
      address === "" ||
      role === ""
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
    <Form>
      <HeaderText value="SignUp" />
      <Form.Group controlId="name">
        <Label value="Name" />
        <TextInput value={name} setValue={setName} />
      </Form.Group>
      <Form.Group controlId="mobileNo">
        <Label value="Mobile No" />
        <NumberInput maxlength="10" value={mobileNo} setValue={setMobileNo} />
      </Form.Group>

      {otpVerified ? (
        <>
          <Form.Group controlId="password">
            <Label value="Password" />
            <PasswordInput value={password} setValue={setPassword} />
          </Form.Group>
          <Form.Group controlId="address">
            <Label value="Address" />
            <Form.Control
              as="textarea"
              rows={3}
              value={address}
              onChange={e => setAddress(e.target.value)}
            />
          </Form.Group>
          <RolesDropdown setRole={setRole} />

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
              <Form.Group controlId="otp">
                <NumberInput value={otp} setValue={setOtp} />
              </Form.Group>
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
    </Form>
  );
};
export default SignUp;
