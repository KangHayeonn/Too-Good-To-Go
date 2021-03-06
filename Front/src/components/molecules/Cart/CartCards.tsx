import React, { useEffect, useState } from "react";
// Styles
import styled from "@emotion/styled";
import ArrowRightAltRoundedIcon from "@mui/icons-material/ArrowRightAltRounded";
// redux
import { useDispatch, useSelector } from "react-redux";
// rtk
import { useLocation } from "react-router-dom";
import {
	selectCartCardByID,
	initialCartCardType,
	incrementSelectedCards,
	decrementSelectedCards,
} from "../../../features/cartFeatures/selectCartCardsSlice";
import { RootState } from "../../../app/store";
// images
import fighting from "../../../../public/image/화이팅도치.jpg";
// cart local storage helper function
import { getLocalStorageCart } from "../../../helpers/cartControl";

// Emotion theme
type theme = {
	checked: boolean;
};

const CartCards: React.FC = () => {
	const [displayCardArr, setDisplayCardArr] = useState<initialCartCardType[]>(
		[]
	);
	const dispatch = useDispatch();
	// Used for useEffect trigger when entered '/cart'
	const location = useLocation();

	// used for useEffect, triggers useEffect with every redux reducer invoke.
	const reduxStateCollector = useSelector((state: RootState) => {
		return state.selectCartCards;
	});
	const state = useSelector((state: RootState) => {
		// Will return cards only with cartItemQuantity >= 1
		const cards = state.selectCartCards.filter((e) => {
			return e.cartItemQuantity;
		});

		return cards;
	});

	// Error fixed, invalid hook call.
	useEffect(() => {
		console.log(location);
		if (!getLocalStorageCart()) {
			setDisplayCardArr(state);
		} else {
			const state = JSON.parse(getLocalStorageCart() || "{}");
			setDisplayCardArr(state);
		}
	}, [location, reduxStateCollector]);

	if (!displayCardArr.length) {
		return (
			<Wrapper>
				<img src={fighting} alt="화이팅도치의 존재" />
			</Wrapper>
		);
	}

	return (
		<Wrapper>
			{displayCardArr.map((card: initialCartCardType) => {
				console.log(card);
				console.log("------------");
				return (
					// emotion conditional css
					<CartCard key={card.id} checked={card.isChecked}>
						<div className="card-img-ctn">
							<img src={card.image} alt="Food" />
						</div>
						<div className="cardInfo">
							<p>{card.shopName}</p>
							{/* 생각해보니, shoptype과 foodType는 존재하지 않음. */}
							{/* <strong>shopType, Pipe, foodType</strong> */}
							<p>{card.name}</p>
						</div>
						<div className="right-wrapper">
							<div className="price-ctn">
								<p className="price">
									<s>({card.price}원)</s>
									<ArrowRightAltRoundedIcon className="right-arrow" />
									<strong>{card.discountedPrice}원</strong>
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
												decrementSelectedCards(card.id)
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
												incrementSelectedCards(card.id)
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
										dispatch(selectCartCardByID(card.id));
									}}
								>
									선택
								</button>
							</div>
						</div>
					</CartCard>
				);
			})}
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
	background-color: ${({ checked }) => checked && `#c2f8b421`};
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
		max-width: 119px;
		margin-right: 20px;
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
		width: 300px;
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
		margin-left: 30px;
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
