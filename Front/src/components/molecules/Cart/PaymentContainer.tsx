import React from "react";
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";

// type accumulatedAmountType = {
// 	cost: number;
// };

const PaymentContainer: React.FC = () => {
	const isCheckedArr = useSelector((state: RootState) => {
		return state.selectCartCards.filter((e) => {
			return e.isChecked;
		});
	});

	const accumulatedAmount: number = isCheckedArr.reduce((accu, curr) => {
		// eslint-disable-next-line no-param-reassign
		accu += curr.shopFoodCost;
		return accu;
	}, 0);
	// console.log(accumulatedAmount);

	return (
		<Wrapper>
			<div className="first-section">
				<p hidden>1</p>
			</div>
			<div className="second-section">
				<p className="menu">떡볶이</p>
				<p className="menuPriceAndDiscount">상품가격(할인가): </p>
			</div>
			<div className="third-section">
				<p hidden>1</p>
			</div>
			<div className="fourth-section">
				<p>총 {accumulatedAmount}원</p>
			</div>
			<div className="fifth-section">
				<button type="button">선택 결제하기</button>
			</div>
		</Wrapper>
	);
};

export default PaymentContainer;

const Wrapper = styled.div`
	width: 251px;
	height: 349px;
	border: 1px solid lightgrey;
	display: flex;
	flex-direction: column;
	justify-content: space-between;

	.first-section {
		height: 58px;
		background-color: #54b689;
	}

	.second-section {
		height: 99px;
		border-bottom: 2px solid grey;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		flex-direction: column;
		p {
			margin-left: 20px;
			margin: 10px;
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
		height: 67px;
	}

	button {
		width: 152px;
		height: 40px;
		background-color: #54b689;
		color: white;
		font-size: 13px;
		font-weight: 700;
		border-radius: 12px;
		margin-top: 14px;
	}

	h1 {
		display: hidden;
	}
`;
