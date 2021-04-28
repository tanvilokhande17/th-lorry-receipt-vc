import React, {useEffect} from "react";
import {getUser} from "../util/User";

import logo from "./../../images/logo.jpg";
import logout from "./../../images/logout.png";

const Header = () => {
  const user = getUser();
  useEffect(() => {
    console.log("Header");
    console.log(user);
  }, []);
  return (
    <div className="header">
      <span style={{flex: 5}}>
        <img className="header-img" src={logo} />
      </span>
      {user && user.isLogin ? (
        <span style={{flex: 1, display: "flex", direction: "row"}}>
          <div style={{flex: 1}}>
            {user.name} <br /> {user.role}
          </div>
          <div style={{flex: 1}}>
            <img className="header-img" src={logout} />
          </div>
        </span>
      ) : null}
    </div>
  );
};
export default Header;
