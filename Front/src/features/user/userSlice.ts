import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import {
	deleteAccessToken,
	deleteRefreshToken,
	getAccessToken,
} from "../../helpers/tokenControl";
import { deleteUserLocalStorage } from "../../helpers/userInfoControl";

const LOGIN_URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)

function LOGOUT() {
	try {
		/* logout api 호출 */
		axios
			.get(`${LOGIN_URL}/logout`, {
				headers: {
					Authorization: `Bearer ${getAccessToken()}`,
				},
			})
			.then((res) => console.log(res))
			.catch((e) => console.error(e));

		// localStorage에서 user 정보 제거
		deleteAccessToken();
		deleteUserLocalStorage();
		deleteRefreshToken();
	} catch (e) {
		console.log("localStorage is not working");
	}
}

export interface UserI {
	id: string;
	email: string | null;
	name: string | null;
	phone: string | null;
	role: string | null;
}

const initialState: UserI = {
	id: "",
	email: "",
	phone: "",
	role: "",
	name: "",
};

// action & reducer를 동시 만듦
const userSlice = createSlice({
	name: "userActions", // A name, used in action types
	initialState, // The initial state for the reducer
	reducers: {
		// reducer
		tempSetUser: (state, action: PayloadAction<UserI>) => {
			return action.payload;
		},
		/*
		tempSetID: (state, action: PayloadAction<string>) => {
			const type = state; // no-param-reassign 에러
			type.id = action.payload;
			// return state;
		},
		*/
		logout: (state) => {
			const type = state;
			type.id = "";
			type.email = "";
			type.name = "";
			type.phone = "";
			type.role = "";
			LOGOUT();
		},
	},
});

export const { tempSetUser, logout } = userSlice.actions;
export default userSlice.reducer;
