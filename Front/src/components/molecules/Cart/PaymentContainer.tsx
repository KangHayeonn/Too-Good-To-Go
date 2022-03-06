import React, { useState } from "react";
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";
import Modal from "./Modal";

type buttonType = {
	children: React.ReactNode;
};

const PaymentContainer: React.FC<buttonType> = ({ children }) => {
	const [isModalOpen, setIsModalOpen] = useState(false);

	const isCheckedArr = useSelector((state: RootState) => {
		return state.selectCartCards.filter((e) => {
			return e.isChecked;
		});
	});

	// Accumulate money for display
	const accumulatedAmount: number = isCheckedArr.reduce((accu, curr) => {
		// eslint-disable-next-line no-param-reassign
		accu += curr.discountedPrice * curr.cartItemQuantity;
		return accu;
	}, 0);

	// Insert thousand comma separator from accumulatedAmount, IIFE used.
	const numberWithCommas = ((x: number) => {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	})(accumulatedAmount);

	const handleClick = () => {
		setIsModalOpen(true);
	};

	return (
		<Wrapper>
			<div className="first-section">
				<p hidden>1</p>
			</div>
			<div className="second-section">
				<p className="menu">
					{isCheckedArr.map((e) => {
						return (
							<li key={e.id}>
								{e.name}
								{e.cartItemQuantity > 1 &&
									` x ${e.cartItemQuantity}`}
							</li>
						);
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
				<button type="button" onClick={() => handleClick()}>
					{children}
				</button>
				{isModalOpen ? <Modal setIsModalOpen={setIsModalOpen} /> : null}
			</div>
		</Wrapper>
	);
};

export default PaymentContainer;

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
		.menuPriceAndDiscount {
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
