/* eslint-disable */

import { useState, useEffect } from "react";
import axios from "axios";

export default function useGetData(state, url, errCallBack) {
	const [value, setValue] = useState(state);

	const api = axios.create({
		baseURL: url,
	});

	useEffect(() => {
		let isCancelled = false;
		const fetchData = async () => {
			try {
				const response = await api.get("");
				if (!isCancelled) {
					setValue(response.data);
				}
			} catch (err) {
				errCallBack(err);
			}
		};

		fetchData();
		return () => {
			isCancelled = true;
		};
	}, []);

	return [value, setValue];
}
