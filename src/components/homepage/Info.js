import React from "react";

const Info = () => {
  return (
    <div className="info">
      <p>
        TransportHub, a Transporter, is planning to transport Goods from one
        location to another location.
        <br /> TransportHub is having details of consignor, goods carrier
        (driver) and consignee.
        <br />
        Before starting transportation, Transporter need to generate and share
        multiple copies of Lorry Receipt that is; consignment note for
        consignor, consignee, and driver.
        <br />
        Instead, Transporter could generate and share verifiable credentials
        against individual personal identities which shall get verified during
        transportation process.
      </p>
    </div>
  );
};
export default Info;
