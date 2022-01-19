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
	name: "selectMenuItems",
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
		deleteSelectedItem: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
		},
		incrementSelectedItems: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.shopId === action.payload) {
					return { ...e, cartItemQuantity: e.cartItemQuantity + 1 };
				}
				return e;
			});
		},
		decrementSelectedItems: (state, action: PayloadAction<number>) => {
			return state
				.map((e) => {
					if (e.shopId === action.payload) {
						return {
							...e,
							cartItemQuantity:
								e.cartItemQuantity > 1
									? e.cartItemQuantity - 1
									: 1,
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
	deleteSelectedItem,
	incrementSelectedItems,
	decrementSelectedItems,
} = selectMenuItemsSlice.actions;

export default selectMenuItemsSlice.reducer;
