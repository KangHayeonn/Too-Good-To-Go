import { createSlice } from "@reduxjs/toolkit";
import {
	orderData,
	orderDataCount,
} from "../../components/molecules/OrderDummyData";

export type orderDataCountType = {
	// eslint-disable-next-line camelcase
	order_completed: number;
	preparing: number;
	completed: number;
	showAll: number;
};
export const initialStatus: orderDataCountType[] = orderDataCount.map(() => {
	return {
		order_completed: 0,
		preparing: 0,
		completed: 0,
		showAll: 0,
	};
});

export const managerOrderCountSlice = createSlice({
	name: "managerOrderCount",
	initialState: initialStatus,
	reducers: {
		countOrder: (state) => {
			const orderCompletedCount = orderData.filter(
				(r) => r.status === "ORDER_COMPLETED"
			).length;
			const prepareCount = orderData.filter(
				(r) => r.status === "PREPARING" || r.status === "WAITING_PICKUP"
			).length;
			const completedCount = orderData.filter(
				(r) =>
					r.status === "CANCELED" || r.status === "PICKUP_COMPLETED"
			).length;
			const allCount = orderData.length;
			return state.map(() => {
				return {
					order_completed: orderCompletedCount,
					preparing: prepareCount,
					completed: completedCount,
					showAll: allCount,
				};
			});
		},
	},
});

export const { countOrder } = managerOrderCountSlice.actions;
export default managerOrderCountSlice.reducer;
