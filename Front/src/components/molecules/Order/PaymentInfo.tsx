import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import { useDispatch } from "react-redux";
import Dropdown from "../../atoms/DropDown/Dropdown";
import { setcachePaymentMethod } from "../../../features/order/orderInfoSlice";

const RequestShop = styled.div`
	display: flex;
	flex-direction: column;
	padding: 1em;
	margin-top: 0.5em;
	margin-bottom: 5.5em;
`;

const Text = styled.div`
	width: 13%;
	padding: 5px;
	padding-left: 21px;
	font-size: 16px;
	font-weight: 600;
	color: #646464;
	display: flex;
	margin-top: 0.5em;
`;

const Button = styled.div`
	width: 200px;
	height: 31px;
	border: none;
	display: flex;
	margin-top: 0.5em;
	margin-left: 1.5em;
	margin-bottom: 2em;
`;

const Input1 = styled.input`
	position: relative;
	vertical-align: middle;
	width: 1.27em;
	height: 1.27em;
	cursor: pointer;
	outline: none !important;
	border: 2px solid #cfcfcf;
	background: #ffffff;
	border-radius: 3px;
`;

const Label = styled.label`
	margin-left: 0.7em;
	font-size: 14px;
	font-weight: 600;
	color: #525252;
`;
const PaymentInfo: React.FC = () => {
	const [check, setCheck] = useState<boolean>(false);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(setcachePaymentMethod(check));
	}, [check]);
	return (
		<>
			<RequestShop>
				<Text>결제수단 선택</Text>
				<Dropdown>결제 수단을 선택해주세요.</Dropdown>
				<Button>
					<Input1
						type="checkbox"
						id="nextusepayment"
						onClick={() => setCheck(!check)}
					/>
					<Label htmlFor="nextusepayment">다음에도 사용</Label>
				</Button>
			</RequestShop>
		</>
	);
};

export default PaymentInfo;
