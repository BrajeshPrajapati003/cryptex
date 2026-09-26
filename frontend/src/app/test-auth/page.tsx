"use client";

import { getCurrentUser } from "@/features/user/api";
import { UserProfileResponse } from "@/features/user/types";
import { useEffect, useState } from "react";

export default function TestAuthPage(){

    const [user, setUser] = useState<UserProfileResponse | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(()=> {
        
        getCurrentUser()
        .then(setUser)
        .catch((error: Error) => {
            setError(error.message);
        });
    }, []);

    if(error){
        return <p>Error: {error}</p>;
    }

    if(!user){
        return <p>Loading...</p>;
    }

    return (
        <pre>
            {JSON.stringify(user, null, 2)}
        </pre>
    );
}