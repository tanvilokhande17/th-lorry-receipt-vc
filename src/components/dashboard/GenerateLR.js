import React, {useEffect, useState} from "react";
import {Form, Col} from "react-bootstrap";
import {HeaderText} from "../common/HeaderText";
import {Label} from "../common/Label";
import {TextInput} from "../common/TextInput";
import {NumberInput} from "../common/NumberInput";
import {SubHeaderText} from "../common/SubHeaderText";
import {SubmitButton} from "../common/SubmitButton";
import Add from "../../images/add_icon.png";
import {LORRY_RECEIPT_URL} from "../util/Constant";
import axios from "axios";
import {getUser} from "../util/User";
import {useHistory} from "react-router-dom";
import {Button} from "react-bootstrap";

const GenerateLR = ({}) => {
  const [consignerNumber, setConsignerNumber] = useState("");
  const [consigneeNumber, setConsigneeNumber] = useState("");
  const [originAddress, setOriginAddress] = useState("");
  const [destinationAddress, setDestinationAddress] = useState("");
  const [driverMobileNumber, setDriverMobileNumber] = useState("");
  const [vehicleNumber, setVehicleNumber] = useState("");
  const [freight, setFreight] = useState("");
  const [advance, setAdvance] = useState("");
  const [toPay, setToPay] = useState("");
  const [count, setCount] = useState(1);
  const [goodsDetails, setGoodsDetails] = useState([]);
  const user = getUser();
  const history = useHistory();

  useEffect(() => {
    if (user && user.isLogin === "true" && user.role === "Transporter") {
    } else history.goBack();
  }, []);

  const submitHandler = e => {
    if (isValid()) {
      let totalWeight = 0;
      goodsDetails.map(good => {
        totalWeight = totalWeight + good.weight;
      });

      const param = {
        transporterMobileNumber: user.mobileNo,
        consignerMobileNumber: consignerNumber,
        consigneeMobileNumber: consignerNumber,
        driverMobileNumber: driverMobileNumber,
        details: {
          vehicleNumber: vehicleNumber,
          loadingAddress: originAddress,
          unloadingAddress: destinationAddress,
          consignments: goodsDetails,
          freight: freight,
          advance: advance,
          toPay: toPay,
          totalWeight: totalWeight,
        },
      };

      axios
        .post(LORRY_RECEIPT_URL, param, {
          headers: {Authorization: "Bearer " + user.accessToken},
        })
        .then(
          response => {
            if (
              response.status === 200 &&
              response.data.message === "success"
            ) {
              alert("Lorry Receipt Created Successfully!");
            } else {
              alert(response.data.errorMessage);
            }
          },
          error => {
            console.log(error);
          }
        );
    } else {
      e.preventDefault();
      alert("Please fill form");
    }
  };
  const isValid = () => {
    if (
      (consignerNumber === "" && consigneeNumber === "") ||
      originAddress === "" ||
      destinationAddress === "" ||
      driverMobileNumber === "" ||
      vehicleNumber === "" ||
      freight === "" ||
      advance === "" ||
      toPay === "" ||
      count === "" ||
      goodsDetails.length === 0
    )
      return false;
    else return true;
  };
  return (
    <div style={{paddingBottom: 80}}>
      <Form className="generateLR-container">
        <Form.Row style={{paddingTop: 20}}>
          <Form.Group as={Col}>
            <HeaderText value="Generate Lorry Reciept" />
          </Form.Group>
          <Form.Group as={Col}>
            <Button style={{float: "right"}} onClick={() => history.goBack()}>
              Back
            </Button>
          </Form.Group>
        </Form.Row>

        <Form.Row style={{paddingTop: 25}}>
          <Form.Group as={Col} style={{paddingRight: 40}}>
            <Label value="Consigner Mobile Number" />
            <NumberInput
              value={consignerNumber}
              setValue={setConsignerNumber}
              placeholder="Enter Consigner Mobile Number"
            />
          </Form.Group>

          <Form.Group as={Col}>
            <Label value="Consignee Mobile Number" />
            <NumberInput
              value={consigneeNumber}
              setValue={setConsigneeNumber}
              placeholder="Enter Consignee Mobile Number"
            />
          </Form.Group>
        </Form.Row>
        <Form.Row>
          <Form.Group as={Col} style={{paddingRight: 40}}>
            <Label value="Place of Origin " />
            <TextInput
              value={originAddress}
              setValue={setOriginAddress}
              placeholder="Enter Place of Origin "
            />
          </Form.Group>

          <Form.Group as={Col}>
            <Label value="Place of Destination" />
            <TextInput
              value={destinationAddress}
              setValue={setDestinationAddress}
              placeholder="Enter Place of Destination "
            />
          </Form.Group>
        </Form.Row>
        <Form.Row>
          <Form.Group as={Col} style={{paddingRight: 40}}>
            <Label value="Driver Mobile Number" />
            <NumberInput
              value={driverMobileNumber}
              setValue={setDriverMobileNumber}
              placeholder="Enter Driver Mobile Number"
            />
          </Form.Group>

          <Form.Group as={Col}>
            <Label value="Vehicle Number" />
            <TextInput
              value={vehicleNumber}
              setValue={setVehicleNumber}
              placeholder="Enter Vehicle Number"
            />
          </Form.Group>
        </Form.Row>
        <Form.Row style={{paddingTop: 20}}>
          <Form.Group as={Col}>
            <SubHeaderText value="Goods Details" />
          </Form.Group>
        </Form.Row>
        {[...Array(count)].map((_, i) => (
          <GoodsDetails
            key={i}
            index={i}
            goodsDetails={goodsDetails}
            setGoodsDetails={setGoodsDetails}
          />
        ))}
        <Form.Row>
          <Form.Group as={Col}>
            <Label value="Add Good" />
            <img
              src={Add}
              style={{height: 30}}
              onClick={() => {
                if (count < 5) setCount(count + 1);
              }}
            ></img>
          </Form.Group>
        </Form.Row>
        <Form.Row style={{paddingTop: 10}}>
          <Form.Group as={Col}>
            <SubHeaderText value="Freight Charges Details" />
          </Form.Group>
        </Form.Row>
        <Form.Row>
          <Form.Group as={Col}>
            <Label value="Total Freight Charges" />
            <NumberInput
              value={freight}
              setValue={setFreight}
              placeholder="Enter Freight Charges"
            />
          </Form.Group>

          <Form.Group as={Col}>
            <Label value="Advance" />
            <NumberInput
              value={advance}
              setValue={setAdvance}
              placeholder="Enter Advance Paid Amount"
            />
          </Form.Group>

          <Form.Group as={Col}>
            <Label value="To Pay" />
            <NumberInput
              value={toPay}
              setValue={setToPay}
              placeholder="Enter Payable Amount"
            />
          </Form.Group>
        </Form.Row>
        <Button onClick={submitHandler} block type="submit" variant="primary">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default GenerateLR;

const GoodsDetails = ({index, goodsDetails, setGoodsDetails}) => {
  const [weight, setWeight] = useState("");
  const [quantity, setQuantity] = useState("");
  const [goodsDescription, setGoodsDescription] = useState("");

  useEffect(() => {
    const temp = {
      quantity: quantity,
      weight: weight,
      description: goodsDescription,
    };

    if (goodsDetails.length <= index) {
      setGoodsDetails([...goodsDetails, temp]);
    } else {
      const goods = goodsDetails;
      goods[index] = temp;
      setGoodsDetails(goods);
    }
  }, [weight, quantity, goodsDescription]);

  return (
    <>
      <Form.Row>
        <Form.Group as={Col} style={{paddingRight: 40}}>
          <Label value="Weight of Goods (in KG)" />
          <NumberInput
            value={weight}
            setValue={setWeight}
            placeholder="Enter Weight of Goods"
          />
        </Form.Group>

        <Form.Group as={Col}>
          <Label value="Quantity of Goods" />
          <NumberInput
            value={quantity}
            setValue={setQuantity}
            placeholder="Enter Quantity of Goods"
          />
        </Form.Group>
      </Form.Row>
      <Form.Row>
        <Form.Group as={Col} controlId="formGridZip">
          <Label value="Description of Goods" />
          <Form.Control
            as="textarea"
            rows={2}
            value={goodsDescription}
            onChange={e => setGoodsDescription(e.target.value)}
            placeholder="Enter Description of Goods"
          />
        </Form.Group>
      </Form.Row>
    </>
  );
};
