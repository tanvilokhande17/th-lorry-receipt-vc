import React, {useState} from "react";
import {Button} from "react-bootstrap";

export const SubmitButton = ({label, disabled, submitHandler}) => {
  return (
    <Button
      variant="primary"
      disabled={disabled}
      block
      onClick={submitHandler}
      type="submit"
    >
      {label}
    </Button>
  );
};
