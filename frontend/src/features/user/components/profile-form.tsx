"use client";

import React, { useState } from "react";
import { updateCurrentUser } from "../api";
import { ApiError } from "@/lib/api/errors";
import { useRouter } from "next/navigation";

interface ProfileFormProps{
    firstName: string;
    lastName: string;
}

export function ProfileForm({
    firstName,
    lastName,
}: ProfileFormProps){
    const [form, setForm] = useState({firstName, lastName,});

    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

                const router = useRouter();


    async function handleSubmit(
        event: React.FormEvent<HTMLFormElement>,
    ){
        event.preventDefault();
        setMessage("");
        setIsSubmitting(true);

        try{
            await updateCurrentUser(form);
            setMessage("Profile updated successfully.");
            router.refresh();
        }catch(error){
            setMessage(
                error instanceof ApiError
                ? error.message
                : "Unable to update profile.",
            );
        }finally{
            setIsSubmitting(false);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="mt-6 max-w-xl space-y-5">
            <div>
                <label className="text-sm text-zinc-400">
                    First Name
                </label>

                <input 
                value={form.firstName}
                onChange={(e)=>
                    setForm({
                        ...form,
                        firstName: e.target.value,
                    })
                }
                className="mt-1 w-full rounded-lg border border-zinc-700 bg-zinc-900 px-4 py-2" />
            </div>

            <div>
                <label className="text-sm text-zinc-400">
                    Last Name
                </label>

                <input
                value={form.lastName}
                onChange={(e)=>
                    setForm({
                        ...form,
                        lastName: e.target.value,
                    })
                }
                className="mt-1 w-full rounded-lg border border-zinc-700 bg-zinc-900 px-4 py-2" 
                />
            </div>

            <button
            type="submit"
            disabled={isSubmitting} 
            className="rounded-lg bg-white px-5 py-2 font-medium text-black disabled:opacity-50">
                {isSubmitting ? "Saving..." : "Save changes"}
            </button>

            {message && <p>{message}</p>}
        </form>
    )
}