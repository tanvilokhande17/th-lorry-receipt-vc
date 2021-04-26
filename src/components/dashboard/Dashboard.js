import React, {useState} from "react";
import {Button, Dropdown, DropdownButton, Table} from "react-bootstrap";
import {useHistory} from "react-router";
import {lrList} from "./LRlist";
import {DownloadPDF} from "./DownloadPDF";

const Dashboard = () => {
  const history = useHistory();

  return (
    <div className="dashboard-container">
      <div className="heading dashboard-header">Lorry Receipt List</div>

      <Button
        className="dashboard-generate-lr"
        variant="light"
        onClick={e => {}}
      >
        Generate LR
      </Button>

      <div className="dashboard-table">
        <Table striped bordered hover variant="light">
          <thead>
            <tr>
              <th>LR No.</th>
              <th>VC ID</th>
              <th>Consigner</th>
              <th>Consignee</th>
              <th>Date</th>
              <th>Status</th>
              <th>Download PDF</th>
            </tr>
          </thead>
          <tbody>
            {lrList.map(lr => (
              <tr>
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
                  {lr.lrNo}
                </td>
                <td>{lr.vcId}</td>
                <td>{lr.consigner}</td>
                <td>{lr.consignee}</td>
                <td>{lr.date}</td>
                <td>
                  <DropdownButton id="dropdown-basic-button" title={lr.status}>
                    <Dropdown.Item id="InTransit">In Transit</Dropdown.Item>
                    <Dropdown.Item id="Loaded">Loaded</Dropdown.Item>
                    <Dropdown.Item id="Delivered">Delivered</Dropdown.Item>
                  </DropdownButton>
                </td>
                <td onClick={() => alert("download PDF of " + lr.lrNo)}>
                  <DownloadPDF lrDetails={lr} />
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
};

export default Dashboard;
