import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface State {
	cost: number;
	saleCost: number;
	deliveryCost: number;
	totalCost: number;
}

const initialState: State = {
	cost: 0,
	saleCost: 0,
	deliveryCost: 2500,
	totalCost: 0,
};

const priceInfoSlice = createSlice({
	name: "priceActions",
	initialState,
	reducers: {
		// reducer
		setPriceItems: (
			state,
			action: PayloadAction<{ cost: number; saleCost: number }>
		) => {
			const type = state;
			type.cost = action.payload.cost;
			type.saleCost = action.payload.saleCost;
			type.totalCost = state.deliveryCost + action.payload.saleCost;
			return state;
		},
	},
});

export const { setPriceItems } = priceInfoSlice.actions;
export default priceInfoSlice.reducer;
