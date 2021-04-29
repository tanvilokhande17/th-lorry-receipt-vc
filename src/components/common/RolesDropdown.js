import React from "react";
import {Form} from "react-bootstrap";

const RolesDropdown = ({setRole}) => {
  return (
    <Form.Group controlId="role">
      <Form.Control
        as="select"
        defaultValue="Transporter"
        onChange={e => setRole(e.target.value)}
      >
        <option value="Transporter">Transporter</option>
        <option value="Consignee">Consignee</option>
        <option value="Consigner">Consigner</option>
        <option value="Driver">Driver</option>
      </Form.Control>
    </Form.Group>
  );
};
export default RolesDropdown;
