import React from "react";
// Styles
import styled from "@emotion/styled";
import CheckBoxOutlineBlankIcon from "@mui/icons-material/CheckBoxOutlineBlank";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import ArrowRightAltRoundedIcon from "@mui/icons-material/ArrowRightAltRounded";
// redux
import { useDispatch, useSelector } from "react-redux";
// rtk
import {
	CartCardType,
	initialCards,
	selectCartCardByID,
} from "../../../features/cartFeatures/selectCartCardsSlice";
import { RootState } from "../../../app/store";

// import { bindActionCreators } from "redux";
// import { NewShopsType } from "../../../CartReducer/state/reducers/cartReducer";
// import { shopData } from "../ShopDummyData";
// import { actionCreators, State } from "../../../CartReducer/state";

export type ShopsType = {
	shopId: number;
	shopFoodImg: string;
	shopName: string;
	shopFoodName: string;
	shopFoodSale: number;
	shopFoodCost: number;
	shopBeforeCost: number;
};

// const newShopData: NewShopsType[] = shopData.map((data) => {
// 	return { ...data, isChecked: false };
// });

const CartCards: React.FC = () => {
	const dispatch = useDispatch();

	// const { checkCartItem } = bindActionCreators(actionCreators, dispatch);

	// useSelector returns the modifiedState from reducer.
	const isCheckedArr = useSelector((state: RootState) => {
		return state.selectCartCards.filter((e) => {
			return e.isChecked;
		});
	});

	return (
		<Wrapper>
			{initialCards.map((card: CartCardType) => {
				return (
					<CartCard key={card.shopId}>
						<div className="card-img-ctn">
							<img src={card.shopFoodImg} alt="Food" />
						</div>
						<div className="cardInfo">
							<p>{card.shopName}</p>
							<strong>shopType, Pipe, foodType</strong>
							<p>{card.shopFoodName}</p>
						</div>
						<div className="right-wrapper">
							<div className="price-ctn">
								<p className="price">
									<s>({card.shopBeforeCost}원)</s>
									<ArrowRightAltRoundedIcon className="right-arrow" />
									<strong>{card.shopFoodCost}원</strong>
								</p>
								{/* checking isChecked */}
								<p>
									{isCheckedArr.find((e) => {
										return e.shopId === card.shopId;
									})
										? "checked"
										: "false"}
								</p>
							</div>
							<div className="btn-ctn">
								<button type="button">수정</button>
								<button
									type="button"
									onClick={() => {
										// console.log(data.shopId);
										dispatch(
											selectCartCardByID(card.shopId)
										);
									}}
								>
									선택
								</button>
								{isCheckedArr.find((e) => {
									return e.shopId === card.shopId;
								}) ? (
									<CheckBoxIcon />
								) : (
									<CheckBoxOutlineBlankIcon />
								)}
							</div>
						</div>
					</CartCard>
				);
			})}
		</Wrapper>
	);
};

export default CartCards;

const Wrapper = styled.div`
	width: 680px;
	height: 579px;
	/* border: 1px solid blue; */
	border-top: 1px solid #d3d3d3;
	overflow: scroll;
	padding: 30px 10px;
	margin-right: 25px;
`;

const CartCard = styled.div`
	width: 640px;
	height: 181px;
	border: 1px solid #d3d3d3;
	padding: 10px 10px 10px 0;
	margin: 25px 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	transition: 0.3s;
	:hover {
		box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
	}

	.card-img-ctn {
		width: 134px;
	}

	img {
		width: 100%;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 12px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 20px;
	}

	.cardInfo > * {
		margin: 6px;
		margin-left: 15px;
	}

	p:last-child {
		font-weight: 600;
	}

	.cardInfo > strong {
		color: #85bf70;
	}

	.right-wrapper {
		margin-left: 160px;
	}

	.btn-ctn {
		/* display: border-box; */
		flex-direction: row;
		width: 250px;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	Button {
		width: 74px;
		height: 27px;
		border-radius: 8px;
		background-color: #cfcfcf;
		color: black;
		font-weight: 500;
		font-size: 13px;
	}

	.price {
		display: flex;
		align-items: center;
		justify-content: center;
		text-align: center;
	}

	.price > s {
		color: black;
	}

	.right-arrow {
		/* margin-top: 20px; */
		/* padding-top */
	}
`;
