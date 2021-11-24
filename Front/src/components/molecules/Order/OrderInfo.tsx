/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Bar = styled.div`
    background-color: blue;
    margin-bottom: 3px;
	padding: 0.7em;
    padding-left : 2.7em;
	display: flex;
	flex-direction: column;
    align-items: flex-start;
    color: blue;
    color: #fff;
    font-size: 19px;
    font-weight: 600;
    box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
`;

const CustomerName = styled.div`
`;

const OrderInfo: React.FC = () => {
    return (
        <div>
        <CustomerName>
            <h4>주문자 *</h4>
            <input
                placeholder = "이름을 입력하세요."
                type = "text"
            />
        </CustomerName>
        <CustomerName>
            <h4>휴대전화 *</h4>
            <input
                placeholder = ""
                type = "text"
            />
            <h4> - </h4>
            <input
                placeholder = ""
                type = "text"
            />
            <h4> - </h4>
            <input
                placeholder = ""
                type = "text"
            />
        </CustomerName>
        <CustomerName>
            <h4>일반전화 </h4>
            <input
                placeholder = ""
                type = "text"
            />
            <h4> - </h4>
            <input
                placeholder = ""
                type = "text"
            />
            <h4> - </h4>
            <input
                placeholder = ""
                type = "text"
            />
        </CustomerName>
        <CustomerName>
            <h4>이메일 </h4>
            <input
                placeholder = ""
                type = "text"
            />
            <h4>@</h4>
            <input
                placeholder = ""
                type = "text"
            />
        </CustomerName>
        <CustomerName>
            <h4>주소 *</h4>
            <input
                placeholder = "우편번호"
                type = "text"
            />
            <input
                placeholder = "기본주소"
                type = "text"
            />
            <input
                placeholder = "나머지 주소"
                type = "text"
            />
        </CustomerName>
        <Bar/>
        </div>
    );
};
export default OrderInfo;