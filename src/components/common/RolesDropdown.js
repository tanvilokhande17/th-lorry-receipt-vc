import React from "react";
import {Dropdown, DropdownButton, FormGroup} from "react-bootstrap";

const RolesDropdown = ({role, setRole}) => {
  return (
    <FormGroup controlId="role">
      <DropdownButton id="dropdown-basic-button" title={role}>
        <Dropdown.Item onSelect={() => setRole("Transporter")} id="Transporter">
          Transporter
        </Dropdown.Item>
        <Dropdown.Item onSelect={() => setRole("Consignee")} id="Consignee">
          Consignee
        </Dropdown.Item>
        <Dropdown.Item onSelect={() => setRole("Consigner")} id="Consigner">
          Consigner
        </Dropdown.Item>
        <Dropdown.Item onSelect={() => setRole("Driver")} id="Driver">
          Driver
        </Dropdown.Item>
      </DropdownButton>
    </FormGroup>
  );
};
export default RolesDropdown;
