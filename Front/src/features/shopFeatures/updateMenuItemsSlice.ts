import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type CartCardType = [
	{
		id: number;
		name: string;
		image: string;
		discountedPrice: number;
		price: number;
		shopId: number;
		shopName: string;
		shopCategory: Array<string>;
	}
];

export const initialCards: CartCardType = [
	{
		id: 0,
		name: "",
		image: "",
		discountedPrice: 0,
		price: 0,
		shopId: 0,
		shopName: "",
		shopCategory: [],
	},
];

export const updateMenuItemsSlice = createSlice({
	name: "selectMenuItems",
	initialState: initialCards,
	reducers: {
		updateMenuItems: (state, action: PayloadAction<CartCardType>) => {
			return action.payload;
		},
	},
});

export const { updateMenuItems } = updateMenuItemsSlice.actions;

export default updateMenuItemsSlice.reducer;
