import React from "react";
import styled from "@emotion/styled";

const RequestShop = styled.div`
	display: flex;
	flex-direction: column;
	padding: 1em;
	margin-top: 0.5em;
`;

const Text = styled.div`
	width: 13%;
	padding: 5px;
	padding-left: 21px;
	font-size: 16px;
	font-weight: 600;
	color: #646464;
	display: flex;
	display: flex;
	margin-top: 0.5em;
`;

const Input = styled.input`
	width: 70%;
	height: 29px;
	padding-left: 15px;
	margin: 1.5em 0 19px 1.7em;
	border: solid 1px #c4c4c4;
	&:focus {
		outline: 1px solid #dfdfdf;
	}
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
	return (
		<div>
			<RequestShop>
				<Text>결제수단 선택</Text>
				<Input
					placeholder="예) 견과류 빼주세요, 덜 맵게 해주세요."
					type="text"
				/>
				<Button>
					<Input1 type="checkbox" id="nextusepayment" />
					<Label htmlFor="nextusepayment">다음에도 사용</Label>
				</Button>
			</RequestShop>
		</div>
	);
};

export default PaymentInfo;