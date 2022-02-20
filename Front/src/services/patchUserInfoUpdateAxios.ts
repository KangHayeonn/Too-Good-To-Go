import axios from "axios";
import { UserT } from "../components/molecules/Profile/FormContainer";
import { getAccessToken } from "../helpers/tokenControl";
import { BASE_URL } from "./baseURL";

const patchUserInfo = (formData: UserT) => {
	return axios.patch("/api/me", formData, {
		baseURL: BASE_URL,
		headers: { Authorization: `Bearer ${getAccessToken()}` },
	});
};

export default { patchUserInfo };
