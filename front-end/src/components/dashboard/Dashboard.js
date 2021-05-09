import React, {useEffect, useState} from "react";
import {Button, Form, Table} from "react-bootstrap";
import {useHistory} from "react-router-dom";
import DownloadPDF from "../downloadPdf/DownloadPDF";
import axios from "axios";
import {LORRY_RECEIPT_URL, UPDATE_STATUS_URL} from "../util/Constant";
import {getUser} from "../util/User";
import GenerateQR from "../downloadPdf/GenerateQR";

const Dashboard = () => {
  const [lrList, setLRList] = useState([]);
  const user = getUser();
  const history = useHistory();

  useEffect(() => {
    if (user && user.isLogin === "true") {
      getLRList();
    } else history.push("/");
  }, []);

  useEffect(() => {
    console.log("lrList updated");
  }, [lrList]);

  const getLRList = () => {
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
            ) {
              const res = response.data.lorryReceipts;
              const list = res.filter(f => f.vcId !== undefined);
              setLRList(list);
            }
          }
        },
        error => {
          console.log(error);
        }
      );
  };

  const updateStatus = (action, lr) => {
    console.log("updateStatus " + action);
    console.log(lr);

    axios
      .post(
        UPDATE_STATUS_URL,
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
            window.location.pathname = "/dashboard";
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
                <th>verification</th>
                <th>Download PDF</th>
              </tr>
            </thead>
            <tbody>
              {lrList.map((lr, i) => (
                <tr key={lr.lorryReceipt.id}>
                  <td
                    className="dashboard-link"
                    onClick={() =>
                      history.push({
                        pathname: "/lr-details",
                        state: {
                          vcId: lr.vcId,
                          status: lr.lorryReceipt.status,
                          VCStored: lr.VCStored,
                          vcUrl: lr.vcUrl,
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
                  <td>
                    {new Date(lr.lorryReceipt.createdAt).getDate() +
                      "-" +
                      parseInt(
                        new Date(lr.lorryReceipt.createdAt).getMonth() + 1
                      ) +
                      "-" +
                      new Date(lr.lorryReceipt.createdAt).getFullYear()}
                  </td>
                  <td>
                    <ActionChange
                      updateStatus={updateStatus}
                      lr={lr}
                      role={user.role}
                    />
                  </td>
                  <td>
                    <GenerateQR
                      lorryReceiptId={lr.lorryReceipt.id}
                      role={user.role}
                      VCStored={lr.VCStored}
                      vcUrl={lr.vcUrl}
                    />
                  </td>
                  <td>
                    <DownloadPDF
                      vcId={lr.vcId}
                      status={lr.lorryReceipt.status}
                      VCStored={lr.VCStored}
                      vcUrl={lr.vcUrl}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        )}
      </div>
    </div>
  );
};

export default Dashboard;

const ActionChange = ({updateStatus, lr, role}) => {
  const [options, setOptions] = useState([]);

  useEffect(() => {
    console.log(lr.lorryReceipt.status + " " + role);
    const status = lr.lorryReceipt.status;
    setAction(status, role, lr);
  }, []);

  const setAction = (status, role, lr) => {
    console.log("ActionChange");
    if (status === "CANCELLED" || status === "COMPLETED") setOptions([status]);
    else if (lr.VCStored === false) setOptions([status, "APPROVE"]);
    else if (role === "Transporter") {
      if (status === "BOOKED") setOptions([status, "CANCELLED"]);
      else setOptions([status]);
    } else if (role === "Consigner") {
      if (status === "BOOKED") setOptions([status, "PICKUP", "CANCELLED"]);
      else setOptions([status]);
    } else if (role === "Driver") {
      if (status === "PICKEDUP") setOptions([status, "PICKUP"]);
      else if (status === "DELAYED") setOptions([status, "INTRANSIT"]);
      else if (status === "INTRANSIT")
        setOptions([status, "DELAYED", "DELIVERY"]);
      else setOptions([status]);
    } else if (role === "Consignee") {
      if (status === "DELIVERED") setOptions([status, "DELIVERY", "DECLINED"]);
      else setOptions([status]);
    }
  };

  return (
    <Form.Control
      as="select"
      disabled={options.length === 1 ? true : false}
      defaultValue={lr.lorryReceipt.status}
      onChange={e => updateStatus(e.target.value, lr)}
    >
      {options.map((option, i) => (
        <option value={option} key={i}>
          {option}
        </option>
      ))}
    </Form.Control>
  );
};
