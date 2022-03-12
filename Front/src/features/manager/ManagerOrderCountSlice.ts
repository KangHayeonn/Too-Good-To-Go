import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type orderDataCountType = {
	// eslint-disable-next-line camelcase
	order_completed: number;
	completed: number;
	canceled: number;
	showAll: number;
};
export const initialStatus: orderDataCountType = {
	order_completed: 0,
	completed: 0,
	canceled: 0,
	showAll: 0,
};

export const managerOrderCountSlice = createSlice({
	name: "managerOrderCount",
	initialState: initialStatus,
	reducers: {
		countOrder: (state, action: PayloadAction<orderDataCountType>) => {
			return {
				order_completed: action.payload.order_completed,
				completed: action.payload.completed,
				canceled: action.payload.canceled,
				showAll: action.payload.showAll,
			};
		},
	},
});

export const { countOrder } = managerOrderCountSlice.actions;
export default managerOrderCountSlice.reducer;
