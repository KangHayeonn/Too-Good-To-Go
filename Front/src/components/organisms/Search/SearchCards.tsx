import React from 'react';
import styled from "@emotion/styled";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { RootState } from "../../../app/store";
import { initialCards } from '../../../features/shopFeatures/updateMenuItemsSlice';

// 할인율 계산
const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

const SearchCards:React.FC = () => {
    const searchItems = useSelector((state: RootState) => {
        return state.updateMenuItems;
    })
    
    return (
        <Wrapper>
            { (searchItems!==initialCards) ? (
                searchItems.map((row) => (
                    <div key={row.shopId}>
						<Link
							to={{
								pathname: `/shop/${row.shopId}`,
							}}>
                        <SearchCard>
                            <div className="card-img-ctn">
                                <img src={row.image} alt="Food" />
                            </div>
                            <div className="cardInfo">
                                <ShopTitle>
                                    <p className="cardInfo-shopName">
                                        {row.shopName}
                                    </p>
                                    <p className="cardInfo-category">
                                        {row.shopCategory.join(", ")}
                                    </p>
                                </ShopTitle>
                                <p className="cardInfo-shopFoodName">
                                    {row.name}
                                </p>
                                <div className="cardInfo-price">
                                    <p>
                                        {calculatedDiscount(
                                            row.price,
                                            row.discountedPrice
                                        )}
                                        %
                                    </p>
                                    <p>{row.discountedPrice}원</p>
                                    <s>({row.price}원)</s>
                                </div>
                            </div>
                        </SearchCard>
						</Link>
                        <Line /> 
                    </div>   
                ))
            ):(<></>)
        }
        </Wrapper>
    );
};

const Wrapper = styled.div`
	width: 820px;
	height: 730px;
	overflow: visible;
	overflow-x: hidden;
	padding-left: 10px;
    padding-right: 10px;
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
    margin-left : 11.5em;
    margin-top : 2em;
`;

const SearchCard = styled.div`
    border: 1px solid #eee;
	width: 805px;
	height: 181px;
	padding: 10px 10px 10px 0;
	margin: 0 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;

	transition: 0.3s;

	.card-img-ctn {
		width: 134px;
		margin-right: 9px;
		margin-left: 10px;
	}

	img {
		width: 161px;
		height: 149px;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 3px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 50px;
		width: 550px;
	}

	.cardInfo > * {
		margin: 2px;
		margin-left: 14px;
	}

	.cardInfo-shopName {
        font-size: 22px;
		font-weight: 700;
        margin-bottom: 13px;
        padding-right: 11px;
        border-right : 3px solid #E2E2E2;
	}

    .cardInfo-category {
        font-size: 22px;
        margin-bottom: 13px;
        margin-left: 11px;
        color: #58C656;
        font-weihgt: 400;
    }

	.cardInfo-shopFoodName {
		font-size: 18px;
	}

	.cardInfo-price {
		display: flex;
		font-size: 17px;
		font-weight: 500;
	}

	.cardInfo-price > p:first-of-type {
		color: #2f8d09;
		padding-right: 5px;
		font-weight: 700;
	}
`;

const ShopTitle = styled.div`
    display : flex;
`

const Line = styled.div`
    margin-top: 1.5em;
    margin-bottom: 1.5em;
    border-bottom: 3px solid #eee;
`

export default SearchCards;