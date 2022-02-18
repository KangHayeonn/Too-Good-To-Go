import { Dispatch } from "@reduxjs/toolkit";
import axios from "axios";
import { getAccessToken } from "../../helpers/tokenControl";
import { setUserLocalStorage } from "../../helpers/userInfoControl";
import { tempSetUser } from "../../features/user/userSlice";

const URL = "http://54.180.134.20/api";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const userAPI = (dispatch: Dispatch<any>) => {
	const token = getAccessToken();

	if (token) {
		axios
			.get(`${URL}/me`, {
				headers: {
					Authorization: `Bearer ${token}`,
				},
			})
			.then((res) => {
				const stringifiedUserInfo = JSON.stringify(res.data.data);
				const { id, email, name, phone, role } = res.data.data;

				setUserLocalStorage(stringifiedUserInfo);
				// setEmail(JSON.stringify(email));

				// 이 부분이 있어야 로그아웃 버튼으로 바로 변경됨 (헤더부분)
				dispatch(tempSetUser({ id, email, name, phone, role }));
				// dispatch(tempSetUser(res.data.data));
			});
	}
};
