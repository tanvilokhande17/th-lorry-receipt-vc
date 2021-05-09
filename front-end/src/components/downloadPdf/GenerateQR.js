import axios from "axios";
import React, {useState} from "react";
import {Button, Modal} from "react-bootstrap";

import {createGlobalStyle} from "styled-components";
import {VERIFICATION_URL} from "../util/Constant";
import {getUser} from "../util/User";

const GenerateQR = ({lorryReceiptId, role}) => {
  const user = getUser();
  const [qrUrl, setQrUrl] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const download = () => {
    

    if (lorryReceiptId && role) {
      const param = {
        lorryReceiptId: lorryReceiptId,
        role: role,
      };
      axios
        .post(VERIFICATION_URL, param, {
          headers: {
            Authorization: "Bearer " + user.accessToken,
          },
        })
        .then(
          response => {
            if (response.status === 200) {
              const res = response.data.qrCode;
              if (res !== undefined && res !== null) {
                setQrUrl(res);
                setShowModal(true);
              } else {
                alert(response.data.message);
              }
            }
          },
          error => {
            console.log(error);
          }
        );
    }
  };

  return (
    <>
      <Button
        className="dashboard-link"
        variant="light"
        onClick={() => download()}
      >
        verification
      </Button>
      {showModal === true ? (
        <QRCodeData
          showModal={showModal}
          setShowModal={setShowModal}
          qrUrl={qrUrl}
          setQrUrl={setQrUrl}
        />
      ) : null}
    </>
  );
};

export default GenerateQR;

export const QRCodeData = ({showModal, setShowModal, qrUrl, setQrUrl}) => {
  const [printContent, setPrintContent] = useState(1);
  const handleClose = () => setShowModal(false);

  return (
    <>
      <Modal
        className="QrCodeModal"
        show={showModal}
        onHide={handleClose}
        animation={false}
      >
        <div className="modal-content">
          <Modal.Header closeButton>
            <Modal.Title>QR Code Image</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div ref={el => setPrintContent(el)}>
              <center>
                <img src={qrUrl} />
              </center>
            </div>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </div>
      </Modal>
    </>
  );
};
