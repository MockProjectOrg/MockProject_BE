package org.example.bookingbe.controller;

import org.example.bookingbe.model.dto.HotelDTO;
import org.example.bookingbe.service.HotelService.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @GetMapping("/hotels")
    public String listHotels(Model model) {
        model.addAttribute("hotels", hotelService.findAllHotels());
        return "admin/hotels";
    }

    @GetMapping("/hotels/add")
    public String showAddHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDTO());
        return "admin/hotel-form-add";
    }

    @PostMapping("/add")
    public String addHotel(@ModelAttribute HotelDTO hotelDTO, RedirectAttributes redirectAttributes) {
        hotelService.createHotel(hotelDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully!");
        return "redirect:/admin/hotels";
    }

    @GetMapping("/hotels/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        HotelDTO hotelDTO = hotelService.findHotelById(id);
        model.addAttribute("hotel", hotelDTO);
        return "admin/hotel-form-edit";
    }

    @PostMapping("/edit/{id}")
    public String editHotel(@PathVariable Long id, @ModelAttribute HotelDTO hotelDTO,
                            BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/hotel-form-edit";
        }

        hotelService.updateHotel(id, hotelDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel updated successfully!");
        return "redirect:/admin/hotels";
    }

    @PostMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteHotel(id);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel deleted successfully!");
        return "redirect:/admin/hotels";
    }
}
