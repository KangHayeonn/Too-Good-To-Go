import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface State {
	orderInfoCheck: string;
	phone: string;
	plasticUse: boolean;
	requirement: string;
	paymentMethod: string;
	cacheRequirement: boolean;
	cachePaymentMethod: boolean;
}

const initialState: State = {
	orderInfoCheck: "",
	phone: "",
	plasticUse: false,
	requirement: "",
	paymentMethod: "",
	cacheRequirement: false,
	cachePaymentMethod: false,
};

const orderInfoSlice = createSlice({
	name: "orderActions",
	initialState,
	reducers: {
		// reducer
		setOrderInfoCheck: (state, action: PayloadAction<string>) => {
			const type = state;
			type.orderInfoCheck = action.payload;
			return state;
		},
		tempSetPhone: (state, action: PayloadAction<string>) => {
			const type = state;
			type.phone = action.payload;
			return state;
		},
		setPlasticUse: (state, action: PayloadAction<boolean>) => {
			const type = state;
			type.plasticUse = action.payload;
			return state;
		},
		setRequirement: (
			state,
			action: PayloadAction<{
				requirement: string;
				cacheRequirement: boolean;
			}>
		) => {
			const type = state;
			type.requirement = action.payload.requirement;
			type.cacheRequirement = action.payload.cacheRequirement;
			return state;
		},
		setPaymentMethod: (state, action: PayloadAction<string>) => {
			const type = state;
			type.paymentMethod = action.payload;
			return state;
		},
		setcachePaymentMethod: (state, action: PayloadAction<boolean>) => {
			const type = state;
			type.cachePaymentMethod = action.payload;
			return state;
		},
	},
});

export const {
	setOrderInfoCheck,
	tempSetPhone,
	setPlasticUse,
	setRequirement,
	setPaymentMethod,
	setcachePaymentMethod,
} = orderInfoSlice.actions;
export default orderInfoSlice.reducer;
