import axios from "axios";
import {
	getAccessToken,
} from "../helpers/tokenControl";

const BASE_URL = "http://54.180.134.20";

// cached_order_info
export const axiosApiCachedOrderInfoGetInstance = axios.create({
	baseURL: BASE_URL,
	url: "/api/cached-order-info",
	method: "GET",
	headers: {
		Authorization: `Bearer ${getAccessToken()}`,
		"Content-Type": "application/json",
	},
});