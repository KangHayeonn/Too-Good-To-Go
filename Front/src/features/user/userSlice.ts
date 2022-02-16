import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import { deleteAccessToken, getAccessToken } from "../../helpers/tokenControl";
import {
	deleteUserID,
	deleteEmail,
	deleteName,
	deletePhone,
	deleteRole,
} from "../../helpers/userInfoControl";

const LOGIN_URL = "http://54.180.134.20/api"; // http 붙여야함 (404 오류 방지)

function LOGOUT() {
	try {
		/* logout api 호출 */
		axios
			.delete(`${LOGIN_URL}/logout`, {
				headers: {
					Authorization: `Bearer ${getAccessToken()}`,
				},
			})
			.then(() => console.log("로그아웃 성공"))
			.catch((e) => console.error(e));

		// localStorage에서 user 정보 제거
		deleteAccessToken();
		deleteUserID();
		deleteEmail();
		deleteName();
		deletePhone();
		deleteRole();
	} catch (e) {
		console.log("localStorage is not working");
	}
}

export interface State {
	id: string;
	email: string | null;
	name: string | null;
	phone: string | null;
	role: string | null;
}

const initialState: State = {
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
		tempSetUser: (state, action: PayloadAction<State>) => {
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
