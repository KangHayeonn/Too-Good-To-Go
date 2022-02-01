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
					response.data.data.sort((a, b) => {
						console.log(a.createdAt, b.createdAt);
						let dateA = new Date(a.createdAt);
						let dateB = new Date(b.createdAt);
						console.log(dateA, dateB);
						return dateB - dateA;
					});
					console.log("response: ", response.data.data);
					setValue(response.data.data);
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
