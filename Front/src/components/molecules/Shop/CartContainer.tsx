import React from "react";
import styled from "@emotion/styled";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../app/store";
// rtk
import {
	selectCartCardByID,
	initialCartCardType,
	incrementSelectedCards,
	decrementSelectedCards,
} from "../../../features/cartFeatures/selectCartCardsSlice";

// type accumulatedAmountType = {
// 	cost: number;
// };

type buttonType = {
	children: React.ReactNode;
};

const CartContainer: React.FC<buttonType> = ({ children }) => {
	const dispatch = useDispatch();

    // logic to display cards
	const displayCardArr = useSelector((state: RootState) => {
		// Will return cards only with cartItemQuantity >= 1
		return state.selectCartCards.filter((e) => {
			return e.cartItemQuantity;
		});
	});

    const isCheckedArr = useSelector((state: RootState) => {
		return state.selectCartCards.filter((e) => {
			return e.isChecked;
		});
	});

	// Accumulate money for display
	const accumulatedAmount: number = isCheckedArr.reduce((accu, curr) => {
		// eslint-disable-next-line no-param-reassign
		accu += curr.shopFoodCost;
		return accu;
	}, 0);

	// Insert thousand comma separator from accumulatedAmount, IIFE used.
	const numberWithCommas = ((x: number) => {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	})(accumulatedAmount);

	return (
		<Wrapper>
			<div className="first-section">
				<p hidden>1</p>
			</div>
			<div className="second-section">
				<p className="menu">
					{isCheckedArr.map((e) => {
						return (
                            <div className="menu-item">
                                <li key={e.shopId}>
								{e.shopFoodName}
								{e.cartItemQuantity > 1 &&
									` x ${e.cartItemQuantity}`}
							    </li>
                                <QuantityButton>
										<button
                                            className="menu-item-button"
											type="button"
											onClick={() => {
												dispatch(
													decrementSelectedCards(
														e.shopId
													)
												);
											}}
										>
											-
										</button>
										<p>{e.cartItemQuantity}</p>
										<button
                                            className="menu-item-button"
											type="button"
											onClick={() => {
												dispatch(
													incrementSelectedCards(
														e.shopId
													)
												);
											}}
										>
											+
										</button>
									</QuantityButton>
                            </div>
                        )
					})}
				</p>
				<p className="menuPriceAndDiscount">상품가격(할인가): </p>
			</div>
			<div className="third-section">
				<p hidden>1</p>
			</div>
			<div className="fourth-section">
				<p>총 {numberWithCommas}원</p>
			</div>
			<div className="fifth-section">
				<button type="button">{children}</button>
			</div>
		</Wrapper>
	);
};

export default CartContainer;

const Wrapper = styled.div`
	width: 271px;
	min-height: 349px;
	height: auto;
	border: 1px solid lightgrey;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	.first-section {
		height: 58px;
		background-color: #54b689;
	}

	.second-section {
		min-height: 99px;
		height: auto;
		border-bottom: 2px solid grey;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		flex-direction: column;
		p {
			margin-left: 20px;
			margin: 10px;
			li {
				margin: 5px;
				list-style: none;
				text-align: left;
			}
		}
		.menu {
			font-size: 20px;
			font-weight: bold;
		}
		.menu-item {
            border-bottom: 1px solid #eee;
		}
	}

	.third-section {
		height: 79px;
		border-bottom: 2px solid grey;
	}
	.fourth-section {
		height: 47px;
		border-bottom: 2px solid grey;
		color: #54b689;
		font-weight: 700;
		font-size: 20px;
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	p {
		margin-right: 15px;
	}
	.fifth-section {
		height: 81px;
	}

	button {
		width: 152px;
		height: 45px;
		background-color: #54b689;
		color: white;
		font-size: 15px;
		font-weight: 700;
		border-radius: 12px;
		margin-top: 18px;
	}

	h1 {
		display: hidden;
	}
`;

const QuantityButton = styled.div`
	width: 80px;
	height: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-direction: row;
	overflow: hidden;
	border-radius: 13px;
	background-color: #e7e4e4;
    margin-left : 170px;
    margin-bottom : 13px;

	.menu-item-button {
		font-size: 17px;
		width: 30px;
		background-color: #e7e4e4;
        padding-bottom: 21px;
	}

    p {
        font-size: 14px;
    }
`;