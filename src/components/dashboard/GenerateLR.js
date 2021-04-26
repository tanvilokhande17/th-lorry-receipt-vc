import React, {useState} from "react";
import {Form, Col, Button} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {SubmitButton} from "../common/SubmitButton";
const GenerateLR = ({setPage}) => {
  const [consignorName, setConsignorName] = useState("");
  const [consigneeName, setConsigneeName] = useState("");
  const [weight, setWeight] = useState("");
  const [quantity, setQuantity] = useState("");
  const [originAddress, setOriginAddress] = useState("");
  const [destinationAddress, setDestinationAddress] = useState("");
  const [bookingDate, setBookingDate] = useState("");
  const [payment, setPayment] = useState("");
  const [goodsDescription, setGoodsDescription] = useState("");

  function validateForm() {
    return consignorName.length > 0 && consigneeName.length > 0;
  }

  return (
    <div className="generateLR-container">
      <Form>
        <HeaderText
          className="generateLRHeader"
          value="Generate Lorry Reciept"
        />

        <Form.Row>
          <Form.Group as={Col} controlId="formGridEmail">
            <Label value="Consignor Name" />
            <Form.Control
              type="text"
              value={consignorName}
              onChange={e => setConsignorName(e.target.value)}
              placeholder="Enter Consignor Name"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridPassword">
            <Label value="Consignee Name" />
            <Form.Control
              type="text"
              value={consigneeName}
              onChange={e => setConsigneeName(e.target.value)}
              placeholder="Enter Consignee Name"
            />
          </Form.Group>
        </Form.Row>

        <Form.Row>
          <Form.Group as={Col} controlId="formGridCity">
            <Label value="Weight of Goods" />
            <Form.Control
              type="text"
              value={weight}
              onChange={e => setWeight(e.target.value)}
              placeholder="Enter Weight of Goods"
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridState">
            <Label value="Quantity of Goods" />
            <Form.Control
              type="text"
              value={quantity}
              onChange={e => setQuantity(e.target.value)}
              placeholder="Enter Quantity of Goods"
            />
          </Form.Group>
        </Form.Row>
        <Form.Row>
          <Form.Group as={Col} controlId="formGridAddress1">
            <Label value="Name and Address of place of origin " />
            <Form.Control
              as="textarea"
              rows={3}
              value={originAddress}
              onChange={e => setOriginAddress(e.target.value)}
              placeholder=""
            />
          </Form.Group>

          <Form.Group as={Col} controlId="formGridAddress2">
            <Label value="Name and Address of place of destination" />
            <Form.Control
              as="textarea"
              rows={3}
              value={destinationAddress}
              onChange={e => setDestinationAddress(e.target.value)}
              placeholder=""
            />
          </Form.Group>
        </Form.Row>

        <Form.Row>
          <Form.Group as={Col} controlId="formGridCity">
            <Label value="Date of booking of the goods" />
            <Form.Control
              value={bookingDate}
              onChange={e => setBookingDate(e.target.value)}
              placeholder="Enter Date of booking"
            />
          </Form.Group>
          <Form.Group as={Col} controlId="formGridState">
            <Label value="To Pay/Paid(freight charges)" />
            <Form.Control
              value={payment}
              onChange={e => setPayment(e.target.value)}
              placeholder=""
            />
          </Form.Group>
        </Form.Row>

        <Form.Row>
          <Form.Group as={Col} controlId="formGridZip">
            <Label value="Description of Goods" />
            <Form.Control
              as="textarea"
              rows={3}
              value={goodsDescription}
              onChange={e => setGoodsDescription(e.target.value)}
              placeholder="Enter Description of Goods"
            />
          </Form.Group>
        </Form.Row>
        <SubmitButton label="submit" submitHandler={() => {}} />
      </Form>
    </div>
  );
};

export default GenerateLR;
