import React, {useEffect, useState} from "react";
import {Button, Form, Table} from "react-bootstrap";
import {useHistory} from "react-router-dom";
import {DownloadPDF} from "./DownloadPDF";
import axios from "axios";
import {LORRY_RECEIPT_URL, UPDATE_STATUS_URL} from "../util/Constant";
import {getUser} from "../util/User";

const Dashboard = () => {
  const [lrList, setLRList] = useState([]);
  const user = getUser();
  const history = useHistory();

  useEffect(() => {
    if (user && user.isLogin === "true") {
      axios
        .get(`${LORRY_RECEIPT_URL}?role=${user.role}`, {
          headers: {Authorization: "Bearer " + user.accessToken},
        })
        .then(
          response => {
            if (response.status === 200) {
              if (
                response.data.lorryReceipts !== undefined &&
                response.data.lorryReceipts !== null
              )
                setLRList(response.data.lorryReceipts);
            }
          },
          error => {
            console.log(error);
          }
        );
    } else history.push("/");
  }, []);

  const updateStatus = (action, lr) => {
    axios
      .post(
        {UPDATE_STATUS_URL},
        {
          lorryReceiptId: lr.lorryReceipt.id,
          action: action,
          role: user.role,
        },
        {
          headers: {Authorization: "Bearer " + user.accessToken},
        }
      )
      .then(
        response => {
          if (response.status === 200) {
          } else {
            alert(response.data.message);
          }
        },
        error => {
          console.log(error);
        }
      );
  };

  return (
    <div className="dashboard-container">
      <div className="heading dashboard-header">Lorry Receipt List</div>
      {user && user.role === "Transporter" ? (
        <Button
          className="dashboard-generate-lr"
          variant="light"
          onClick={() => history.push("/generateLR")}
        >
          Generate LR
        </Button>
      ) : null}

      <div className="dashboard-table">
        {lrList.length === 0 ? null : (
          <Table striped bordered hover variant="light">
            <thead>
              <tr>
                <th>LR No.</th>
                <th>VC ID</th>
                <th>Transporter</th>
                <th>Consigner</th>
                <th>Consignee</th>
                <th>Date</th>
                <th>Status</th>
                <th>Download PDF</th>
              </tr>
            </thead>
            <tbody>
              {lrList.map((lr, i) => (
                <tr key={i}>
                  <td
                    className="dashboard-link"
                    onClick={() =>
                      history.push({
                        pathname: "/lr-details",
                        state: {
                          lrDetails: lr,
                        },
                      })
                    }
                  >
                    {lr.lorryReceipt.receiptNumber}
                  </td>
                  <td>
                    {lr.vcId !== undefined
                      ? lr.vcId.replace("claimId:", "")
                      : null}
                  </td>
                  <td>{lr.transporter.fullName}</td>
                  <td>{lr.consigner.fullName}</td>
                  <td>{lr.consignee.fullName}</td>
                  <td>{lr.lorryReceipt.createdAt}</td>
                  <td>
                    <Form.Control
                      selected={lr.lorryReceipt.status}
                      as="select"
                      title="Status"
                      onChange={e => updateStatus(e.target.value, lr)}
                    >
                      <option value="Loaded">Loaded</option>
                      <option value="InTransit">InTransit</option>
                      <option value="Delivered">Delivered</option>
                    </Form.Control>
                  </td>
                  <td>
                    <DownloadPDF lrDetails={lr}></DownloadPDF>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        )}
        ;
      </div>
    </div>
  );
};

export default Dashboard;
