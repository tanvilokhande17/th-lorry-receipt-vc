export const saveUser = (name, role, accessToken, isLogin) => {
  localStorage.setItem("name", name);
  localStorage.setItem("role", role);
  localStorage.setItem("accessToken", accessToken);
  localStorage.setItem("isLogin", isLogin);
};

export const getUser = () => {
  return createUser(
    localStorage.getItem("name"),
    localStorage.getItem("role"),
    localStorage.getItem("accessToken"),
    localStorage.getItem("isLogin")
  );
};

export const createUser = (name, role, accessToken, isLogin) => {
  return {
    name,
    role,
    accessToken,
    isLogin,
  };
};
