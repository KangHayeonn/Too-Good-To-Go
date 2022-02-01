/* eslint-disable */
import Dispatch from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { orderType } from "../molecules/Profile/ProfileOrderListContainer";

export default function useGetData(
	state: Map<string, orderType[]>,
	url: string,
	errCallBack: (arg0: unknown) => void
) {
	const [value, setValue] = useState<Map<string, orderType[]>>(state);

	const api = axios.create({
		baseURL: url,
	});

	useEffect(() => {
		let isCancelled = false;
		const fetchData = async () => {
			try {
				const response = await api.get("");
				if (!isCancelled) {
					response.data.data.forEach((element: orderType) => {
						element.createdAt = new Date(element.createdAt);
					});
					response.data.data.sort(
						(a: orderType, b: orderType): number => {
							return (
								b.createdAt.getTime() - a.createdAt.getTime()
							);
						}
					);

					const tempValue = response.data.data;
					const map = new Map();

					tempValue.forEach((val: orderType) => {
						const dateString = `${val.createdAt.getFullYear()} ${
							val.createdAt.getMonth() + 1
						} ${val.createdAt.getDate()}`;

						if (!Array.isArray(map.get(dateString))) {
							map.set(dateString, []);
						}
						map.get(dateString).push(val);
					});

					setValue(map);
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

	return [value, setValue] as const;
}
