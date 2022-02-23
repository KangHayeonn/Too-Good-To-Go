// rtk
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { shopData3 } from "../../components/molecules/ShopDummyData";

export type CartCardType = {
	id: number;
	name: string;
	image: string;
	discountedPrice: number;
	price: number;
	shopId: number;
	shopName: string;
};

// CartCardType -> productsDataType
export type initialCartCardType = CartCardType & {
	isChecked: boolean;
	cartItemQuantity: number;
};

export const initialCards: initialCartCardType[] = shopData3.map(
	(e: CartCardType) => {
		return { ...e, isChecked: false, cartItemQuantity: 1 };
	}
);

export const selectMenuItemsSlice = createSlice({
	name: "selectMenuItems",
	initialState: initialCards,
	reducers: {
		selectMenuCard: (state, action: PayloadAction<CartCardType[]>) => {
			return action.payload.map(
				(e: CartCardType) => {
					return {...e, isChecked: false, cartItemQuantity: 1};
				}
			)
		},
		selectCartCardByID: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.id === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
		},
		deleteSelectedItem: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.id === action.payload) {
					return { ...e, isChecked: !e.isChecked };
				}
				return e;
			});
		},
		incrementSelectedItems: (state, action: PayloadAction<number>) => {
			return state.map((e) => {
				if (e.id === action.payload) {
					return { ...e, cartItemQuantity: e.cartItemQuantity + 1 };
				}
				return e;
			});
		},
		decrementSelectedItems: (state, action: PayloadAction<number>) => {
			return state
				.map((e) => {
					if (e.id === action.payload) {
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
	selectMenuCard,
	selectCartCardByID,
	deleteSelectedItem,
	incrementSelectedItems,
	decrementSelectedItems,
} = selectMenuItemsSlice.actions;

export default selectMenuItemsSlice.reducer;
