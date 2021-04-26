import React, {useState} from "react";
import {
  FormGroup,
  Dropdown,
  DropdownButton,
  FormText,
  Form,
} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {PasswordInput} from "../common/PasswordInput";
import {SubmitButton} from "../common/SubmitButton";
import {TextInput} from "../common/TextInput";

const SignUp = ({setPage}) => {
  const [name, setName] = useState("");
  const [mobileNo, setMobileNo] = useState("");
  const [otp, setOtp] = useState("");
  const [showVerifyOtp, setShowVerifyOtp] = useState(false);
  const [otpVerified, setOtpVerified] = useState(false);
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("Select Role");
  const [address, setAddress] = useState("");

  function validateForm() {
    return name.length > 0;
  }

  return (
    <>
      <HeaderText value="SignUp" />
      <FormGroup controlId="name">
        <Label value="Name" />
        <TextInput value={name} setValue={setName} />
      </FormGroup>
      <FormGroup controlId="mobileNo">
        <Label value="Mobile No" />
        <TextInput value={mobileNo} setValue={setMobileNo} />
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
          <FormGroup controlId="role">
            <DropdownButton id="dropdown-basic-button" title={role}>
              <Dropdown.Item
                onSelect={e => setRole("Transporter")}
                id="Transporter"
              >
                Transporter
              </Dropdown.Item>
              <Dropdown.Item
                onSelect={e => setRole("Consignee")}
                id="Consignee"
              >
                Consignee
              </Dropdown.Item>
              <Dropdown.Item
                onSelect={e => setRole("Consigner")}
                id="Consigner"
              >
                Consigner
              </Dropdown.Item>
              <Dropdown.Item onSelect={e => setRole("Driver")} id="Driver">
                Driver
              </Dropdown.Item>
              <Dropdown.Item onSelect={e => setRole("Other")} id="Other">
                Other
              </Dropdown.Item>
            </DropdownButton>
          </FormGroup>

          <SubmitButton
            label="SignUp"
            submitHandler={() => alert(address)}
          ></SubmitButton>
        </>
      ) : (
        <>
          {showVerifyOtp ? (
            <>
              <FormGroup controlId="otp">
                <TextInput value={otp} setValue={setOtp} />
              </FormGroup>
              <SubmitButton
                label="Verify OTP"
                submitHandler={() => setOtpVerified(true)}
              ></SubmitButton>
            </>
          ) : (
            <SubmitButton
              label="Send OTP"
              submitHandler={() => setShowVerifyOtp(true)}
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
