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
	selectCartCardByID,
	initialCartCardType,
	incrementSelectedCards,
	decrementSelectedCards,
} from "../../../features/cartFeatures/selectCartCardsSlice";
import { RootState } from "../../../app/store";
// images
import fighting from "../../../../public/image/화이팅도치.jpg";

// Emotion theme
type theme = {
	checked: boolean;
};

const CartCards: React.FC = () => {
	const dispatch = useDispatch();

	// logic to display cards
	const displayCardArr = useSelector((state: RootState) => {
		// Will return cards only with cartItemQuantity >= 1
		return state.selectCartCards.filter((e) => {
			return e.cartItemQuantity;
		});
	});

	if (displayCardArr.length) {
		return (
			<Wrapper>
				{displayCardArr.map((card: initialCartCardType) => {
					return (
						<CartCard key={card.shopId} checked={card.isChecked}>
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
								</div>
								<div className="btn-ctn">
									{/* <button type="button">수정</button> */}
									{/* Card quantity button component */}
									<QuantityButton>
										<button
											type="button"
											onClick={() => {
												dispatch(
													decrementSelectedCards(
														card.shopId
													)
												);
											}}
										>
											-
										</button>
										<p>{card.cartItemQuantity}</p>
										<button
											type="button"
											onClick={() => {
												dispatch(
													incrementSelectedCards(
														card.shopId
													)
												);
											}}
										>
											+
										</button>
									</QuantityButton>
									<button
										className="select-btn"
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

									{/* removed checkbox icon with shadow rgba */}
									{/* {card.isChecked ? (
										<CheckBoxIcon />
									) : (
										<CheckBoxOutlineBlankIcon />
									)} */}
								</div>
							</div>
						</CartCard>
					);
				})}
			</Wrapper>
		);
	}
	return (
		<Wrapper>
			<img src={fighting} alt="화이팅도치의 존재" />
		</Wrapper>
	);
};

export default CartCards;

const QuantityButton = styled.div`
	width: 142px;
	height: 27px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-direction: row;
	overflow: hidden;
	border-radius: 13px;
	background-color: #e7e4e4;

	button {
		font-size: 20px;
		width: 55px;
		background-color: #e7e4e4;
	}
`;

const Wrapper = styled.div`
	width: 700px;
	height: 579px;
	/* border: 1px solid blue; */
	border-top: 1px solid #d3d3d3;
	overflow: visible;
	overflow-x: hidden;
	padding: 30px 10px;
	margin-right: 25px;
	::-webkit-scrollbar {
		width: 10px;
	}
	::-webkit-scrollbar-thumb {
		background-color: #e7e4e4;
		border-radius: 12px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	::-webkit-scrollbar-track {
		background-color: white;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
`;

const CartCard = styled.div<theme>`
	width: 660px;
	height: 181px;
	border: 1px solid #d3d3d3;
	padding: 10px 10px 10px 0;
	margin: 25px 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;
	/* box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); */
	box-shadow: ${({ checked }) =>
		checked
			? `0 4px 8px 1px rgba(84, 182, 137, 0.5)`
			: `0 4px 8px 0 rgba(0, 0, 0, 0.1)`};
	transition: 0.3s;
	:hover {
		box-shadow: ${({ checked }) =>
			checked
				? `0 8px 16px 1px rgba(84, 182, 137, 0.8)`
				: ` 0 8px 16px 0 rgba(0, 0, 0, 0.2)`};
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
		margin: 10px;

		.select-btn {
			width: 74px;
			height: 27px;
			border-radius: 8px;
			background-color: #cfcfcf;
			color: black;
			font-weight: 500;
			font-size: 13px;
			margin: 3px;
		}
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
