/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Bar = styled.div`
	padding: 2em;
`;

const CustomerName = styled.div`
	display: flex;
`;

const Text = styled.div`
	width: 13%;
	padding: 5px;
	padding-left: 29px;
	font-size: 15px;
	font-weight: 600;
	color: #646464;
	display: flex;
`;

const Input = styled.input`
	width: 23%;
	height: 27px;
	padding-left: 15px;
	margin-bottom: 19px;
	border: solid 1px #c4c4c4;

	&:focus {
		outline: 1px solid #dfdfdf;
	}
`;

const Sign = styled.h4`
	margin: 5px 1em 1em;
`;

const OrderInfo: React.FC = () => {
	return (
		<div>
			<CustomerName>
				<Text>주문자 *</Text>
				<Input placeholder="이름을 입력하세요." type="text" />
			</CustomerName>
			<CustomerName>
				<Text>휴대전화 *</Text>
				<Input placeholder="" type="text" className="phone" />
				<Sign> - </Sign>
				<Input placeholder="" type="text" />
				<Sign> - </Sign>
				<Input placeholder="" type="text" />
			</CustomerName>
			<Bar />
		</div>
	);
};
export default OrderInfo;
