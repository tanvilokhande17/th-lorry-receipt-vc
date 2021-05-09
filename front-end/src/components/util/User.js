export const saveUser = (name, mobileNo, role, accessToken, isLogin) => {
  localStorage.setItem("name", name);
  localStorage.setItem("mobileNo", mobileNo);
  localStorage.setItem("role", role);
  localStorage.setItem("accessToken", accessToken);
  localStorage.setItem("isLogin", isLogin);
};

export const getUser = () => {
  return createUser(
    localStorage.getItem("name"),
    localStorage.getItem("mobileNo"),
    localStorage.getItem("role"),
    localStorage.getItem("accessToken"),
    localStorage.getItem("isLogin")
  );
};

export const createUser = (name, mobileNo, role, accessToken, isLogin) => {
  return {
    name,
    mobileNo,
    role,
    accessToken,
    isLogin,
  };
};
