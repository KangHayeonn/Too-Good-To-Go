import axios from "axios";
import { getAccessToken } from "../../helpers/tokenControl";
import {
	setUserID,
	setEmail,
	setName,
	setPhone,
	setRole,
} from "../../helpers/userInfoControl";

const URL = "http://54.180.134.20/api";

export const userAPI = () => {
	const token = getAccessToken();

	if (token) {
		axios
			.get(`${URL}/me`, {
				headers: {
					Authorization: `Bearer ${token}`,
				},
			})
			.then((res) => {
				const { id, email, name, phone, role } = res.data.data;
				setUserID(id);
				// setEmail(JSON.stringify(email));
				setEmail(email);
				setName(name);
				setPhone(phone);
				setRole(role);
			});
	}
};
