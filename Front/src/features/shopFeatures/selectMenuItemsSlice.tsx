// rtk
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { shopData } from "../../components/molecules/ShopDummyData";

export type CartCardType = {
	shopId: number;
	shopFoodImg: string;
	shopName: string;
	shopFoodName: string;
	shopFoodSale: number;
	shopFoodCost: number;
	shopBeforeCost: number;
};

export type initialCartCardType = CartCardType & {
	isChecked: boolean;
	cartItemQuantity: number;
};

export const initialCards: initialCartCardType[] = shopData.map(
	(e: CartCardType) => {
		return { ...e, isChecked: false, cartItemQuantity: 1 };
	}
);

export const selectMenuItemsSlice = createSlice({
	name: "selectCartCards",
	initialState: initialCards,
	reducers: {
		selectCartCardByID: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
		},
		deleteSelectedCards: (state) => {
			let filteredArrOfState: initialCartCardType[] = [];
			filteredArrOfState = state.filter((e) => {
				return e.isChecked === false;
			});

			return filteredArrOfState;
		},
		incrementSelectedCards: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, cartItemQuantity: e.cartItemQuantity + 1 };
				}
				return e;
			});
		},
		decrementSelectedCards: (state, action: PayloadAction<number>) => {
			// Deletes card which has cartItemQuantity <= 0
			return state
				.map((e) => {
					if (e.shopId === action.payload) {
						return {
							...e,
							cartItemQuantity: e.cartItemQuantity - 1,
						};
					}
					return e;
				})
				.filter((e) => {
					return e.cartItemQuantity;
				});
		},
	},
});

// Action creators are generated for each case reducer function
export const {
	selectCartCardByID,
	deleteSelectedCards,
	incrementSelectedCards,
	decrementSelectedCards,
} = selectMenuItemsSlice.actions;

export default selectMenuItemsSlice.reducer;
